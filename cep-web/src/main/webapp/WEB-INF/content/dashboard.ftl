<script language="javascript" type="text/javascript" src="${base}/js/chart.min.js"></script>
 <!--[if lte IE 8]>
	<script language="javascript" type="text/javascript" src="${base}/js/excanvas.min.js"></script>
<![endif]-->
<script type="text/javascript" language="javascript">
		var colorList = ['#5da5da','#faa43a','#60bd68','#f17cb0','#b2912f','#b276b2','#decf3f','#f15854','#4d4d4d'];
	  $(document).ready(function(){

			$.ajax({
				type: 'GET',
				dataType: 'json',
				contentType: 'application/x-www-form-urlencoded',
				url: '${base}/rest/alertsdistribution',
				success: function(data) {
					var source = [];
					var c = 0;
					for (prop in data) {
						if (data.hasOwnProperty(prop)) {
							if (c === 9) {
								c = 0;
							}
							source.push( { label: prop, value: data[prop], color: colorList[c++] });
						}
					}
		  		var ctx = document.getElementById('chart').getContext('2d');
		  		var chart = new Chart(ctx).Pie(source);
		  		$('#legendContainer').append(chart.generateLegend());
				}
			});
	  });
</script>
<div class="row-fluid">
	<div class="span8">
		<h2>Alert distribution</h2>
		<div class="row-fluid chart-container">
			<div class="span8">
				<canvas id="chart" height="300" width="300"></canvas>
			</div>
			<div class="span4" id="legendContainer">

			</div>
		</div>
	</div>
	<div class="span4">
		<h2>Recent alerts</h2>
	</div>
</div>
