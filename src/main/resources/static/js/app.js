$(function () {
  var TOKEN_KEY = "jwtToken"
  var $token = $("#token").hide();
  var $getToken = $("#getToken");
  var $getClaim = $("#getClaim");

  $("#loginForm").submit(function(e) {
    e.preventDefault();

//    var formData = {
//        username: $(this).find('input[name="username"]').val(),
//        password: $(this).find('input[name="password"]').val()
//    };
    var testdata = 'username=' + $(this).find('input[name="username"]').val() + '&password=' + $(this).find('input[name="password"]').val();
    $.ajax({
        data: testdata,
        timeout: 1000,
        type: 'POST',
        url: '/login',

//      type: "POST",
//      url: "/login",
//      contentType: "application/json; charset=utf-8",
//      data: JSON.stringify(formData),
      success: function (data) {
        setJwtToken(data.jwt);
        showTokenInformation();
      },
      error: function (err) {
        console.log(err);
      }
    });
  });

  $getClaim.click(function(){
    showUserInformation();
  });

  $('#getUser').click(function(){
    $.ajax({
      url: "/whoami",
      type: "GET",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      headers: createAuthorizationTokenHeader(),
      success: function (data) {
        console.log(data);
      },
      error: function (err) {
        console.log(err);
      }
    });
  });


  $('#getAllUser').click(function(){
    $.ajax({
      url: "/allUsers",
      type: "GET",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      headers: createAuthorizationTokenHeader(),
      success: function (data) {
        console.log(data);
      },
      error: function (err) {
        console.log(err);
      }
    });
  });



  function getJwtToken() {
    return localStorage.getItem(TOKEN_KEY);
  }

  function setJwtToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
  }


  function showUserInformation() {
    $.ajax({
        url: "/test-parse",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: createAuthorizationTokenHeader(),
        success: function (data) {
          console.log(data);
        },
        error: function (err) {
          console.log(err);
        }
    });
  }

  function showTokenInformation() {
      $token
          .text("{ Token: " + getJwtToken() )
          .show();
  }

  function createAuthorizationTokenHeader() {
      var token = getJwtToken();
      if (token) {
          return {"Authorization": token};
      } else {
          return {};
      }
    }

});
