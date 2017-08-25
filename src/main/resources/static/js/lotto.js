$(document).ready(function(){
   $("#historySelector").change(function(){
      var otherUser = $("#historySelector").val();
      var currentUsername = $("#currentUsername").text();

      window.location.href = "?user=" + currentUsername + "&historyUser=" + otherUser;
   });
});