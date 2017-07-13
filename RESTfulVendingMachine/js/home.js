var moneyInMachine = 0.00;
$(document).ready(function () {

    $('h1').css('text-align', 'center');
    $('#money-added').css('border', 'solid black');
    $('#money-added').css('padding-top', '15px');
    $('#money-added').css('padding-bottom', '15px');
    $('#money-added').css('width', '50%');
    $('#item-name').css('border', 'solid black')
        .css('padding-top', '15px')
        .css('padding-bottom', '15px')
        .css('width', '125%');
    $('#show-change').css('border', 'solid black')
        .css('padding-top', '15px')
        .css('padding-bottom', '15px')
        .css('width', '75%');
    $('#show-message').css('border', 'solid black')
        .css('padding-top', '15px')
        .css('padding-bottom', '15px')
        .css('width', '75%');
    loadItems();
    
    $('#add-dollar').click(function (event) {
        moneyInMachine = moneyInMachine + 1.00;
        moneyInMachine = round(moneyInMachine, 2);
        showMoney(moneyInMachine);
        return moneyInMachine;
    });
    $('#add-dime').click(function (event) {
        moneyInMachine = moneyInMachine + 0.10;
        moneyInMachine = round(moneyInMachine, 2);
        showMoney(moneyInMachine);
        return moneyInMachine;
    });
    $('#add-quarter').click(function (event) {
        moneyInMachine = moneyInMachine + 0.25;
        moneyInMachine = round(moneyInMachine, 2);
        showMoney(moneyInMachine);
        return moneyInMachine;
    });
    $('#add-nickel').click(function (event) {
        moneyInMachine = moneyInMachine + 0.05;
        moneyInMachine = round(moneyInMachine, 2);
        showMoney(moneyInMachine);
        return moneyInMachine;
    });
    $('#item1').click(function (event) {
        hideNumRemClass();
        $('#item-number1').toggle('slow');
        $('#item-name').text('Snickers');
        $('#buy-item').addClass('item-1');
    });
    $('#item2').click(function (event) {
        hideNumRemClass();
        $('#item-number2').toggle('slow');
        $('#item-name').text("M&M's");
        $('#buy-item').addClass('item-2');
    });
    $('#item3').click(function (event) {
        hideNumRemClass();
        $('#item-number3').toggle('slow');
        $('#item-name').text('Almond Joy');
        $('#buy-item').addClass('item-3');
    });
    $('#item4').click(function (event) {
        hideNumRemClass();
        $('#item-number4').toggle('slow');
        $('#item-name').text('Milky Way');
        $('#buy-item').addClass('item-4');
    });
    $('#item5').click(function (event) {
        hideNumRemClass();
        $('#item-number5').toggle('slow');
        $('#item-name').text('PayDay');
        $('#buy-item').addClass('item-5');
    });
    $('#item6').click(function (event) {
        hideNumRemClass();
        $('#item-number6').toggle('slow');
        $('#item-name').text("Reese's");
        $('#buy-item').addClass('item-6');
    });
    $('#item7').click(function (event) {
        hideNumRemClass();
        $('#item-number7').toggle('slow');
        $('#item-name').text('Pringles');
        $('#buy-item').addClass('item-7');
    });
    $('#item8').click(function (event) {
        hideNumRemClass();
        $('#item-number8').toggle('slow');
        $('#item-name').text('Cheezits');
        $('#buy-item').addClass('item-8');
    });
    $('#item9').click(function (event) {
        hideNumRemClass();
        $('#item-number9').toggle('slow');
        $('#item-name').text('Doritos');
        $('#buy-item').addClass('item-9');
    });
    
    $('#buy-item').click(function (event) {
        $('#errorMessages').empty();
        $('#show-message').empty()
        purchaseItem(moneyInMachine);
    });

    $('#change-back').click(function (event) {
        returnChange(moneyInMachine);
    });

    
})

function returnChange(money) {
    var dict = { 'quarters': 0, 'dimes': 0, 'nickels': 0 };
    if (money !== 0.00) {
        var check = true;
        while (check) {
            if (money >= 0.25) {
                money = money - 0.25;
                money = round(money, 2);
                dict.quarters += 1;
                continue;
            }
            if (money >= 0.10) {
                money = money - 0.10;
                money = round(money, 2);
                dict.dimes += 1;
                continue;
            }
            if (money >= 0.05) {
                money = money - 0.05;
                money = round(money, 2);
                dict.nickels += 1;
                continue;
            }
            check = false;
        }
        var changeMessage = '';
        for (var key in dict) {
            if (dict[key] > 0) {
                changeMessage += dict[key] + ' ' + key;
            }
        }
        $('#show-change').text(changeMessage);
        moneyInMachine = 0.00;
        showMoney(moneyInMachine);
    } else {
        $('#show-change').text('No Change Inserted');
    }

}



function round(value, decimals) {
    return Number(Math.round(value + 'e' + decimals) + 'e-' + decimals);
}

function hideNumRemClass() {
    $('#item-name').text('');
    for (var i = 1; i <= 9; i++) {
        $('#item-number' + i).hide();
        $('#buy-item').removeClass('item-' + i);
    }
}

function purchaseItem(money) {
    var number = 0;
    for (var i = 1; i <= 9; i++) {
        if ($('#buy-item').hasClass('item-' + i)) {
            number = i;
        }
    };
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/money/' + money + '/item/' + number,
        'dataType': 'json',
        success: function (data, status) {

            var quarters = data.quarters;
            var dimes = data.dimes;
            var nickels = data.nickels;
            var pennies = data.pennies;
            var line = '';
            if (quarters !== 0) {
                line += quarters + ' quarters ';
            }
            if (dimes !== 0) {
                line += dimes + ' dimes ';
            }
            if (nickels !== 0) {
                line += nickels + ' nickels ';
            }
            if (pennies !== 0) {
                line += pennies + ' pennies ';
            }
            if (quarters === 0 && dimes === 0
                && nickels === 0 && pennies === 0) {
                line += 'No Change';
            }
            $('#show-change').text(line);
            $('#show-message').text('Thank You');
            moneyInMachine = 0.00;
            showMoney(moneyInMachine);
            removeBlocks();
            loadItems();
        },
        error: function (jqXhr) {
            if (jqXhr.status === 422) {
                var responseJSON = jqXhr.responseJSON;
                display422Error(responseJSON.message);
            } else {
                displayErrorMessage();
            }
        }
    })
    return false;
}

function loadItems() {

    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/items',
        success: function (itemArray) {
            $.each(itemArray, function (index, item) {

                var id = item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                var idBlock = id;
                var block = '<p>' + name + '<br/>';
                block += '$' + price + '<br/>';
                block += 'Quantity Left: ' + quantity;
                block += '</p>';
                $('#item-number' + id).text(idBlock).css('text-align', 'left');
                $("#item-number" + id).hide();
                $('#item' + id).append(block);
            });
            return itemArray;
        },
        error: function () {
            displayErrorMessage();
        }
    })
}

function removeBlocks() {
    for (var i = 1; i <= 9; i++) {
        $('#item' + i).children().last().remove();
    }
}

function display422Error(message) {
    $("#show-message").text(message);
}

function displayErrorMessage() {
    $("#errorMessages").append($("<li>")
        .attr({ class: "list-group-item list-group-item-danger" })
        .text("Error calling web service. Please try again later."));
}

function showMoney(moneyInMachine) {
    $('#money-added').text('$' + moneyInMachine);
}
