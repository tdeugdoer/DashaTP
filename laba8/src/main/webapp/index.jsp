<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trigonometric Functions Calculator</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container p-4">
    <h1 class="text-center my-4">Trigonometric Functions Calculator</h1>
    <form action="trig" method="POST" class="my-4">
        <label for="function" class="mx-2">Select function:</label>
        <select id="function" name="function" class="form-control mx-2">
            <option value="sin">sin</option>
            <option value="cos">cos</option>
            <option value="tan">tan</option>
        </select>
        <br><br>
        <label for="value" class="mx-2">Enter value:</label>
        <input type="number" id="value" name="value" required class="form-control mx-2" min="0">
        <br><br>
        <label for="precision" class="mx-2">Enter precision:</label>
        <input type="number" id="precision" name="precision" required class="form-control mx-2" min="0">
        <br><br>
        <input type="submit" value="Calculate" class="btn btn-primary btn-block mx-2">
    </form>

    <h1 class="text-center p-4">Result: ${result}</h1>
    <h2 class="text-center p-2">Degrees: ${degrees}</h2>
    <h2 class="text-center p-2">Radians: ${radians}</h2>

</div>
</body>
</html>