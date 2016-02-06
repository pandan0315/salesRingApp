
$('.wrapper').on('click', function() {
  $(".overlay" ).fadeIn( 200, function() {});
  $(".popup" ).fadeIn( 600, function() {});
});
$('.overlay').on('click', function() {
  $(".overlay" ).fadeOut( 600, function() {});
  $(".popup" ).fadeOut( 200, function() {});
});