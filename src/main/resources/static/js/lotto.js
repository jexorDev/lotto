$(document).ready(function(){
   $("#historySelector").change(function(){
      var otherUser = $("#historySelector").val();
      var currentUsername = $("#currentUsername").text();

      window.location.href = "?user=" + currentUsername + "&historyUser=" + otherUser;
   });

    $(".transaction.old").hide();
    $("#toggleHistory").click(function(){
       $(".transaction.old").slideToggle();

       if ($("#toggleHistory").text() == "Show All"){
           $("#toggleHistory").text("Hide Old");
       } else {
           $("#toggleHistory").text("Show All");
       }
   });

   $(".ticket.old").hide();
   $("#toggleTickets").click(function(){
       $(".ticket.old").slideToggle();

       if ($("#toggleTickets").text() == "Show All"){
           $("#toggleTickets").text("Hide Old");
       } else {
           $("#toggleTickets").text("Show All");
       }
   });

});