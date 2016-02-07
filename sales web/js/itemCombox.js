$(function(){
  
  var combox = $('#combox');
  
  var type = $('#type');
  var type_arr = [];
  
  var merk = $('#merk');
  var merk_arr = [];
  
  var kendaraan = $('#kendaraan');
  var kendaraan_arr = ['Education', 'Hobbies', 'Others'];
  
  var i;
  
  type.hide();
  merk.hide();
  
  for (i = 0; i < kendaraan_arr.length; i++)
  {
    kendaraan.append('<option value="'+kendaraan_arr[i]+'">'+kendaraan_arr[i]+'</option>');
  }
  
  kendaraan.change(function(e){
    
    type.child().remove();
    
    if(this.value == 'Education')
    {
      type_arr = ['bebek', 'matic', 'kopling'];
    }
    
    else if(this.value == 'Hobbies')
    {
      type_arr = ['matic', 'kopling'];
    }
    
    else if(this.value == 'Others')
    {
      type_arr = ['gigi', 'bmx', 'fixie'];
    }
    
    for(i = 0; i < type_arr.length; i++)
    {
      type.append('<option value="'+type_arr[i]+'">'+type_arr[i]+'</option>');
    }
    
    type.show('slow');
    
  });
  
});