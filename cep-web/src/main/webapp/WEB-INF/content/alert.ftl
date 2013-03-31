<style>
	.datatables-wrap-bottom .alertsTable_info
	{
		line-height:20px;
	}
</style>

<script type="text/javascript" language="javascript">
	  $(document).ready(function(){
			var resultsTable = $("#alertsTable").dataTable( {
				"sDom": '<"datatables-wrap clear"><"datatables-wrap clear"<"fl_left"fl>>rt<"datatables-wrap-bottom clear"ip>',
				//"bJQueryUI": true,
				"sPaginationType": "full_numbers",
				"aaSorting": [[0,'desc']],
				"iDisplayLength": 50,
				"aLengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]],
				"asStripClasses": 	[ 'even' ]
			});
	  });
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0"  id="alertsTable">
	<thead>
		<tr>
			<th scope="col" align="left"><a href="#">Start Time<span></span></a></th>
			<th scope="col" align="left"><a href="#">Monitor Name<span></span></a></th>
			<th scope="col" align="left"><a href="#">Criticality<span></span></a></th>
			<th scope="col" align="left"><a href="#">Message<span></span></a></th>
		</tr>
	</thead>
	<tbody>
	<@s.iterator value="alerts" status="rowStatus">
		<tr class="<@s.if test="#rowStatus.odd == true "></@s.if><@s.else>line</@s.else>">
			<td><a href="${base}<@s.property value="controllerNamespace"/>/<@s.property value="id"/>"><@s.property value="startTime.time"/></a></td>
			<td><@s.property value="monitor.name"/></td>
			<td><@s.property value="monitor.criticalityTypeCode"/></td>
			<td><@s.property value="message"/></td>
		</tr>
	</@s.iterator>
	</tbody>
</table>