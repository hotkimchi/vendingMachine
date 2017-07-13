<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/vendingmachine.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-9 text-center">
                    <h1>Vending Machine</h1>
                    <hr/>
                    <ul class="list-group list-group-item-danger" value="${errorMessage}"></ul>
                    <c:forEach items="${items}" var="item">
                        <div class="col-md-3">
                            <form method="GET" action="itemSelect">
                                <button type="submit" class="btn btn-block btn-lg" name="itemButton" value="${item.id}">
                                    <p class="item-number text-left"><c:out value="${item.id}"/></p>
                                    <p class="text-center"><c:out value="${item.name}"/></p>
                                    <p class="text-center"><c:out value="$${item.price}"/></p>
                                    <p class="text-center"><c:out value="Quantity: ${item.quantity}"/></p>
                                </button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-md-3 text-center">
                    <h2>Current Money</h2>

                    <form method="GET" action="addMoney">
                        <input value="$${moneyInserted}" name="addMoney" class="message-box" disabled/>
                        <br/>
                        <button type="submit" class="btn btn-default" name="addMoney" value="Add Dollar" >Add Dollar</button>
                        <button type="submit" class="btn btn-default" name="addMoney" value="Add Quarter" >Add Quarter</button>
                        <br/>
                        <button type="submit" class="btn btn-default" name="addMoney" value="Add Dime" >Add Dime</button>
                        <button type="submit" class="btn btn-default" name="addMoney" value="Add Nickel" >Add Nickel</button>
                    </form>
                    <hr/>
                    <h2>Message</h2>
                    <input value="${message}" class="message-box" name="showMessage" disabled/><br/>
                    <input value="${selectedItem}" class="message-box" name="showItem" disabled/>
                    <form method="GET" action="makePurchase">
                        <button type="submit" class="btn btn-lg">Make Purchase</button>
                    </form>
                    <hr/>
                    <h2>Change</h2>
                    <input value="${change}" class="message-box" name="show-change" disabled/>
                    <form metthod="GET" action="getChange">
                        <button type="submit" class="btn btn-lg" >Get Change Back</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>


</html>

