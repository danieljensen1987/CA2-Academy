  $(document).ready(function() {

        fetchAll();
        deletePerson();
        initPersons();
        initAddBtn();
        initCancelBtn();
        initSaveBtn();
        initEditBtn();
        initUpdateBtn();
      
      });
      
      
      function initAddBtn(){
        $("#btn_add").click(function(){
          initSaveDetails(true);
          fetchAll();
        });
      }
      
      function initSaveBtn(){
        $("#btn_save").click(function(){
          //First create post argument as a JavaScript object
          var newPerson = {"fName" : $("#fname").val(), "lName" : $("#lname").val(), "phone" : $("#phone").val(), "email" : $("#email").val()};
          $.ajax({
            url: "../person",
            data: JSON.stringify(newPerson), //Convert newPerson to JSON
            type : "post",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText+": "+textStatus);    
                    }
          }).done(function(newPerson){
            $("#id").val(newPerson.id);
            initSaveDetails(false);
            fetchAll();
          });
        });
      }
      
      function initUpdateBtn(){
        $("#btn_update").click(function(){
          //First create post argument as a JavaScript object
          var newPerson = {"fName" : $("#fname").val(), "lName" : $("#lname").val(), "phone" : $("#phone").val(), "email" : $("#email").val()};
            $.ajax({
            url: "../person/" + $("#id").val(),
            data: JSON.stringify(newPerson), //Convert newPerson to JSON
            type : "put",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText+": "+textStatus);    
                    }
          }).done(function(newPerson){
//            $("#id").val(newPerson.id);
            initUpdateDetails(false);
            fetchAll();
          });
        });
      }
      
      function initEditBtn(){
        $("#btn_edit").click(function(){
          initUpdateDetails(true);
//          fetchAll();
        });
      }
      
     function initCancelBtn(){
        $("#btn_cancel").click(function(){
          clearDetails();
          initSaveDetails(false);
          fetchAll();
        });
      }
      
      
      function initPersons(){
        $("#persons").click(function(e) {
          var id = e.target.id;
          if (isNaN(id)) {
            return;
          }
          updateDetails(id);
        });
      }
      
      function deletePerson(){
         $("#delete").click(function() {
          $.ajax({
            url: "../person/"+ $("#persons option:selected").attr("id"),
            type: "DELETE",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                        alert(jqXHR.responseText+": "+textStatus);
                    }
          }).done(function(){
            fetchAll();
          });
        });
      }
      
      function initSaveDetails(init){
        if(init){
          $("#fname").removeAttr("disabled");
          $("#lname").removeAttr("disabled");
          $("#phone").removeAttr("disabled");
          $("#email").removeAttr("disabled");
          $("#btn_save").removeAttr("disabled");
          $("#btn_cancel").removeAttr("disabled");
          $("#btn_add").attr("disabled","disabled");
          $("#btn_edit").attr("disabled","disabled");
          $("#btn_update").attr("disabled","disabled");
        }
        else{
          $("#fname").attr("disabled","disabled");
          $("#lname").attr("disabled","disabled");
          $("#phone").attr("disabled","dsiabled");
          $("#email").attr("disabled","dsiabled");
          $("#btn_save").attr("disabled","disabled");
          $("#btn_cancel").attr("disabled","disabled");
          $("#btn_update").attr("disabled","disabled");
          $("#btn_add").removeAttr("disabled");
          $("#btn_edit").removeAttr("disabled");
        }
      }
      
      function initUpdateDetails(init){
        if(init){
          $("#fname").removeAttr("disabled");
          $("#lname").removeAttr("disabled");
          $("#phone").removeAttr("disabled");
          $("#email").removeAttr("disabled");
          $("#btn_update").removeAttr("disabled");
          $("#btn_cancel").removeAttr("disabled");
          $("#btn_add").attr("disabled","disabled");
          $("#btn_edit").attr("disabled","disabled");
          $("#btn_save").attr("disabled","disabled");
        }
        else{
          $("#fname").attr("disabled","disabled");
          $("#lname").attr("disabled","disabled");
          $("#phone").attr("disabled","dsiabled");
          $("#email").attr("disabled","dsiabled");
          $("#btn_save").attr("disabled","disabled");
          $("#btn_cancel").attr("disabled","disabled");
          $("#btn_update").attr("disabled","disabled");
          $("#btn_add").removeAttr("disabled");
          $("#btn_edit").removeAttr("disabled");
        }
      }
      
      function clearDetails(){
         $("#id").val("");
          $("#fname").val("");
          $("#lname").val("");
          $("#phone").val("");
          $("#email").val("");
      }
      function updateDetails(id){
        $.ajax({
          url: "../person/"+id,
          type: "GET",
          dataType: 'json',
          error: function (jqXHR, textStatus, errorThrown) {
                        alert(jqXHR.getResonseText +": "+textStatus);
                    }
        }).done(function(person){
          $("#id").val(person.id);
          $("#fname").val(person.fName);
          $("#lname").val(person.lName);
          $("#phone").val(person.phone);
          $("#email").val(person.email);
        });
      }

      function fetchAll() {
        $.ajax({
          url: "../person",
          type: "GET",
          dataType: 'json',
          error: function(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
          }
        }).done(function(persons) {
          var options = "";
          persons.forEach(function(person) {
            options += "<option id=" + person.id + ">" + person.fName[0] + ", " + person.lName + "</option>";
          });
          $("#persons").html(options);
          clearDetails();
        });
      }