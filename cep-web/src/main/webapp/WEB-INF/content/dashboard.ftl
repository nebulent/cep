<script language="javascript" type="text/javascript" src="${base}/flot/jquery.flot.js"></script>
<script language="javascript" type="text/javascript" src="${base}/flot/jquery.flot.pie.js"></script>
<script type="text/javascript" language="javascript">
	  $(document).ready(function(){

			$.ajax({
				type: 'GET',
				dataType: 'json',
				contentType: 'application/x-www-form-urlencoded',
				url: '${base}/rest/alertsdistribution',
				success: function(data) {
					var source = [];
					for (prop in data) {
						if (data.hasOwnProperty(prop)) {
							source.push( { label: prop, data: data[prop] });
						}
					}
			  		$.plot($("#chart"), source, 
						{
							series: {
								pie: { 
									show: true
								}
							}
						});
					
				}
			});
	  });
</script>
<div class="row-fluid">
	<div class="span8">
		<h2>Alert distribution</h2>
		<div id="chart" style = "height: 300px"></div>
	</div>
	<div class="span4">
		<h2>Recent alerts</h2>
	</div>
</div>