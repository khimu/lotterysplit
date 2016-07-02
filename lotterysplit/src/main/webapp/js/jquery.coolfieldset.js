
function showHide(serverUrl){
  $('#chartit').toggle();

  if($('#chartit').is(":visible")){
    $('#viewchart').attr('src', serverUrl + '/images/btn_viewchart.png');
  }else{
    $('#viewchart').attr('src', serverUrl + '/images/btn_viewchart_text.png');
  }
}