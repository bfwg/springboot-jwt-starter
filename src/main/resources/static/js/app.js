
$(function () {
  var TOKEN_KEY = "access_token"
  var $getToken = $("#getToken");
  var $getClaim = $("#getClaim");
  $("#dashboard").hide();
  $("#login-card").hide();
  if (getAccessToken()) {
    showDashboard(getAccessToken());
  } else {
    showLogin();
  }
  // fake login


  $("#loginForm").submit(function(e) {
    e.preventDefault();
    $.post( "/login", $(this).serialize(), function(data, textStatus) {
      localStorage.setItem(TOKEN_KEY, token);
      showTokenInformation(data);
    });
  });

  $('#signoutButton').click(function(){
    localStorage.removeItem(TOKEN_KEY);
    showLogin();
  });

  $('#parseToken').click(function(){
    $.ajax({
      url: "/parse-token",
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
      url: "/user/all",
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

  function getAccessToken() {
    return localStorage.getItem(TOKEN_KEY);
  }

  function showLogin(token_info) {
    $("#login-card").show();
    $("#dashboard").hide();
  }

  function showDashboard(token_info) {

    $.ajax({
      url: "/parse-token",
      type: "GET",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      headers: createAuthorizationTokenHeader(),
      success: function (data) {
        $("#dashboard").show();
        $("#token").text(jsonBeautifier(token_info));
        $("#token-header").text(jsonBeautifier(data.header));
        $("#token-payload").text(jsonBeautifier(data.payload));
        $("#token-signature").text(jsonBeautifier(data.signature));
        $("#login-card").hide();
      },
      error: function (err) {
        console.log(err);
      }
    });
  }

  function jsonBeautifier(str) {
    return JSON.stringify(str, null, 2);
  }

  function createAuthorizationTokenHeader() {
      var token = getAccessToken();
      if (token) {
          return {"Authorization": "Bearer " + token};
      } else {
          return {};
      }
    }

});
