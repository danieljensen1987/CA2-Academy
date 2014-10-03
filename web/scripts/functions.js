$(document).ready(function () {
    initGetPersonBtn();
    initClearBtn();
    initDisableDiscriptionBtn();
    initAddPersonBtn();
    initDeletePersonBtn();
    initUpdateBtn();
    initAddRoleBtn();
});

function initGetPersonBtn() {
    $("#btn_getPerson").click(function () {
        var id = $("#id").val();
        getDetails(id);
    });
}

function getDetails(id) {
    $.ajax({
        url: "../person/" + id,
        type: "GET",
        dataType: 'json'

    }).done(function (person) {
        $("#id").val(person.id);
        $("#fname").val(person.fName);
        $("#lname").val(person.lName);
        $("#phone").val(person.phone);
        $("#email").val(person.email);
        $("#roles").html("");
        var size = person.roles.length;
        var result = "";
        if (size === undefined) {
            $("#roles").Prepend("Person has no roles");
        }
        
        for (i = 0; i < size; i++) {
            switch (person.roles[i].rolename) {
                case "Student":
                    result = "<div><label>"
                            + "Role " + (i + 1) + "</label><input id='sRole' type='text' value='"
                            + person.roles[i].rolename + "'></div>"
                            + "<div><label>"
                            + "Semester</label><input id='sSemester' type='text' value='"
                            + person.roles[i].semester + "'></div>"
                            ;
                    $("#roles").prepend(result);
                    break;
                case "Teacher":
                    result = "<div><label>"
                            + "Role " + (i + 1) + "</label><input id='tRole' type='text' value='"
                            + person.roles[i].rolename + "'></div>"
                            + "<div><label>"
                            + "Degree</label><input id='tDegree' type='text' value='"
                            + person.roles[i].degree + "'></div>"
                            ;
                    $("#roles").prepend(result);
                    break;
                case "AssistantTeacher":
                    result = "<div><label>"
                            + "Role " + (i + 1) + "</label><input id='aRole' type='text' value='"
                            + person.roles[i].rolename + "'></div>"
                            ;
                    $("#roles").prepend(result);
                    break;
            }
        }

    }).fail(function () {
        $("#status").html("No person exist with the given ID");
    });
}

function initClearBtn() {
    $("#btn_clear").click(function () {
        $("#id").val("");
        $("#fname").val("");
        $("#lname").val("");
        $("#phone").val("");
        $("#email").val("");
        $("#roles").html("");
    });
}

function initDisableDiscriptionBtn() {
    $("#select_role").change(function () {
        if ($(this).val() === "AssitantTeacher") {
            $("#disrole").attr("disabled", "disabled");
        } else {
            $("#disrole").removeAttr("disabled", "disabled");
        }
    });
}

function initAddPersonBtn() {
    $("#btn_addPerson").click(function () {
        var newPerson = {"fName": $("#fname").val(), "lName": $("#lname").val(), "phone": $("#phone").val(), "email": $("#email").val()};
        $.ajax({
            url: "../person",
            data: JSON.stringify(newPerson),
            type: "post",
            dataType: 'json'
        }).done(function (newPerson) {
            $("#id").val(newPerson.id);
        }).fail(function () {
            $("#status").val("Email already exist");
        });
    });
}

function initDeletePersonBtn() {
    $("#btn_deletePerson").click(function () {
        var id = $("#id").val();
        $.ajax({
            url: "../person/" + id,
            type: "DELETE",
            dataType: 'json'
        }).done(function () {
            $("#status").val("Person " + id + " is deleted");
        });
    });
}

function initUpdateBtn() {
    $("#btn_updatePerson").click(function () {
        alert("hej");
        var newPerson = {"fName": $("#fname").val(), "lName": $("#lname").val(), "phone": $("#phone").val(), "email": $("#email").val()};
        $.ajax({
            url: "../person/" + $("#id").val(),
            data: JSON.stringify(newPerson),
            type: "put",
            dataType: 'json'
        }).done(function (newPerson) {
            getDetails(newPerson.id);
        }).fail(function () {
            $("#status").val("Error");
        });
    });
}

function initAddRoleBtn() {
    $("#btn_addRole").click(function () {
        
        var role = $("#select_role option:selected").val();
        var id = $("#id").val();
        var newRole = {"id": id, "roleName": role, "discription": $("#discription").val()};
        $.ajax({
            url: "../role",
            data: JSON.stringify(newRole),
            type: "post",
            dataType: 'json'
        }).done(function () {
            getDetails(id);
        }).fail(function () {
            $("#status").val("Error");
        });
    });
}