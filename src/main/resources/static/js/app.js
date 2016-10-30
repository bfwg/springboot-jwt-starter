$(function () {
  var TOKEN_KEY = "jwtToken"
  var $token = $("#token").hide();
  var $getToken = $("#getToken");
  var $getClaim = $("#getClaim");

  $getToken.click(function(){
    getToken();
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


  function getJwtToken() {
    return localStorage.getItem(TOKEN_KEY);
  }

  function setJwtToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
  }

  function getToken() {
    $.ajax({
      url: "/login",
      type: "GET",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        setJwtToken(data.token);
        showTokenInformation();
      },
      error: function (err) {
        console.log(err);
      }
    });
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
        error: function () {
          console.log('error!');
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
