<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <% String actionType = (String) request.getAttribute("actionType");
           if ("v".equals(actionType)) { %>
            View Recipe
        <% } else if ("e".equals(actionType)) { %>
            Edit Recipe
        <% } else { %>
            Create Recipe
        <% } %>
    </title>
</head>
<body>

    <h1>
        <% actionType = (String) request.getAttribute("actionType");
           if ("v".equals(actionType)) { %>
            View Recipe
        <% } else if ("e".equals(actionType)) { %>
            Edit Recipe
        <% } else { %>
            Create Recipe
        <% } %>
    </h1>

    <% if (request.getAttribute("errorMessage") != null && !((String) request.getAttribute("errorMessage")).isEmpty()) { %>
        <div style="color: red;"><%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <form:form action="<%=request.getContextPath()%>/<%
        String actionTypeForm = (String) request.getAttribute("actionType");
        if ("e".equals(actionTypeForm)) { %>saveRecipe<% }
        else if ("c".equals(actionTypeForm)) {%>createRecipe<% }
         else { %> <% } %>" method="post" modelAttribute="recipe">
        <form:hidden path="id" />  <!-- Hidden, unchangeable ID -->

        <label for="name">Name:</label>
        <form:input path="name" id="name" readonly="<%="v".equals(request.getAttribute("actionType"))%>" /><br/>

        <label for="category">Category:</label>
        <form:input path="category" id="category" readonly="<%="v".equals(request.getAttribute("actionType"))%>" /><br/>

        <label for="description">Description:</label>
        <form:textarea path="description" id="description" readonly="<%="v".equals(request.getAttribute("actionType"))%>" /><br/>

        <label>Ingredients:</label><br/>
        <% String actionTypeIngredients = (String) request.getAttribute("actionType");
           if ("v".equals(actionTypeIngredients)) { %>
            <%
                java.util.List<String> ingredients = (java.util.List<String>) request.getAttribute("ingredients");
                if (ingredients != null) {
                    for (String ingredient : ingredients) {
            %>
                        * <%= ingredient %><br/>
            <%
                    }
                }
            %>
        <% } else { %>
           <%
             java.util.List<String> ingredients = (java.util.List<String>) ((Others.Recipe) request.getAttribute("recipe")).getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
            %>
                <input type="text" name="ingredients[<%= i %>]" value="<%= ingredients.get(i) %>" /><br/>
             <%}%>
                <input type="text" name="ingredients[<%= ingredients.size() %>]" /><br/>
        <% } %>

        <label>Instructions:</label><br/>
        <% String actionTypeInstructions = (String) request.getAttribute("actionType");
           if ("v".equals(actionTypeInstructions)) { %>
            <%
                java.util.List<String> instructions = (java.util.List<String>) request.getAttribute("instructions");
                if (instructions != null) {
                    int instructionNumber = 1;
                    for (String instruction : instructions) {
            %>
                        <%= instructionNumber %>. <%= instruction %><br/>
            <%
                        instructionNumber++;
                    }
                }
            %>
        <% } else { %>
                <%
                 java.util.List<String> instructions = (java.util.List<String>) ((Others.Recipe) request.getAttribute("recipe")).getInstructions();
                    for (int i = 0; i < instructions.size(); i++) {
                %>
                  <input type="text" name="instructions[<%= i %>]" value="<%= instructions.get(i) %>" /><br/>
                <%}%>
                    <input type="text" name="instructions[<%= instructions.size() %>]" /><br/>
            <% } %>

        <label>Date Added:</label>
        <%
           java.util.Date dateAdded = (java.util.Date) ((Others.Recipe) request.getAttribute("recipe")).getDateAdded();
           java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
           String formattedDateAdded = (dateAdded != null) ? sdf.format(dateAdded) : "";
        %>
        <input type="text" value="<%= formattedDateAdded %>" readonly="true"/><br/>

        <label>Date Latest Change:</label>
        <%
           java.util.Date dateLatestChange = (java.util.Date) ((Others.Recipe) request.getAttribute("recipe")).getDateLatestChange();
           java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("dd/MM/yyyy");
           String formattedDateLatestChange = (dateLatestChange != null) ? sdf2.format(dateLatestChange) : "";
        %>
        <input type="text" value="<%= formattedDateLatestChange %>" readonly="true"/><br/>

        <% String actionTypeFinal = (String) request.getAttribute("actionType");
           if ("v".equals(actionTypeFinal)) { %>
            <button type="button" onclick="window.location.href='<%=request.getContextPath()%>/WASD'">OK</button>
        <% } else { %>
             <button type="submit">
              <% if ("e".equals(actionTypeFinal)) { %>
                Save
            <% } else { %>
                Create
            <% } %>
             </button>
             <button type="button" onclick="window.location.href='<%=request.getContextPath()%>/WASD'">Cancel</button>
        <% } %>
    </form:form>

</body>
</html>