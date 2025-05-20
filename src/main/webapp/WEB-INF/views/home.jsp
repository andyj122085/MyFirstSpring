<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h2>使用者清單</h2>
    <ul>
        <c:forEach var="user" items="${users}">
            <li>${user.name}（${user.age} 歲）</li>
        </c:forEach>
    </ul>
</body>
</html>
