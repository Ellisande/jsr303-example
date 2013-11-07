<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form class="form-narrow form-horizontal" method="post" modelAttribute="enhancedSignupForm">

    <fieldset>
        <legend>Please Sign Up</legend>
        <form:errors path="" element="p" class="text-error"/>
        <div class="form-group">
            <label for="userName" class="col-lg-2 control-label">User Name</label>
            <div class="col-lg-10">
                <form:input path="userName" class="form-control" cssErrorClass="form-control" id="userName" placeholder="User Name"/>
                <form:errors path="userName" element="span" class="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-lg-2 control-label">Email</label>
            <div class="col-lg-10">
                <form:input path="email" class="form-control" cssErrorClass="form-control" id="email" placeholder="Email address"/>
                <form:errors path="email" element="span" class="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-lg-2 control-label">Password</label>
            <div class="col-lg-10">
                <form:password path="password" class="form-control" id="password" placeholder="Password"/>
                <form:errors path="password" element="span" class="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <label for="favoriteNumber" class="col-lg-2 control-label">Favorite Number</label>
            <div class="col-lg-10">
                <form:input path="favoriteNumber" cssErrorClass="form-control" class="form-control" id="favoriteNumber" placeholder="e.g. 10011001101"/>
                <form:errors path="favoriteNumber" element="span" class="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <label for="publicProfile" class="col-lg-2 control-label">Make Profile Public</label>
            <div class="col-lg-10">
                <form:checkbox path="isPublicProfile" class="form-control" cssErrorClass="form-control" id="isPublicProfile" placeholder="e.g. 10011001101" title="Yes"/>
                <form:errors path="isPublicProfile" element="span" class="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <button type="submit" class="btn btn-default">Sign up</button>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <p>Already have an account? <a href='<s:url value="/signin"/>'>Sign In</a></p>
            </div>
        </div>
    </fieldset>
</form:form>
