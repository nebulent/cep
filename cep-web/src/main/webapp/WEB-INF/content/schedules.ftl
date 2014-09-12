<div class="content schedules">

	<div class="divider">
		<span>Schedules</span>
	</div>

	<div class="fldcont wmarg clear">
		<input id="new_schedule_btn" type="button" name="new" value=" New Schedule " class=""/>
	</div>
	<form id="scheduleForm" style="display:none">

		<font class="required">*</font>Schedule Name<br>
		<input type="text" name="schedule_name" id="schedule_name" size="48" value="">

		<div class="row-fluid">
			<div class="span5">
				<fieldset>
					<legend>Time Expression</legend>
					<div class="row-fluid">
						<div class="span4">
							<br>
							Every (0/)<input type="checkbox" name="schedule_every_minute" id="schedule_every_minute">
						</div>
						<div class="span4">
							Minutes<br>
							<select name="schedule_minutes" id="schedule_minutes" size="5" class="input-small">
								<option value="*" selected="selected">All Values(*)</option>
								<#list 0..9 as index>
									<option value="#{index}">0#{index}</option>
								</#list>
								<#list 10..59 as index>
									<option value="#{index}">#{index}</option>
								</#list>
							</select>
						</div>
						<div class="span4">
							Hours<br>
							<select name="schedule_hours" id="schedule_hours" size="5" class="input-small">
								<option value="*" selected="selected">All Values(*)</option>
								<#list 0..9 as index>
									<option value="#{index}">0#{index}</option>
								</#list>
								<#list 10..23 as index>
									<option value="#{index}">#{index}</option>
								</#list>
							</select>
						</div>
					</div>
				</fieldset>
			</div>
			<div class="span7">
				<fieldset>
					<legend>Date Expression</legend>
					<div class="row-fluid">
						<div class="span4">
							Days of Month<br>
							<select name="schedule_days_of_month" id="schedule_days_of_month" size="5" class="input-medium">
								<option value="*" selected="selected">All Values(*)</option>
								<option value="?">No Specific Value(?)</option>
								<option value="L">Last</option>
								<#list 1..9 as index>
									<option value="#{index}">0#{index}</option>
								</#list>
								<#list 10..31 as index>
									<option value="#{index}">#{index}</option>
								</#list>
							</select>
						</div>
						<div class="span4">
							Month<br>
							<select name="schedule_month" id="schedule_month" size="5" class="input-medium">
								<option value="*" selected="selected">All Values(*)</option>
								<option value="?">No Specific Value(?)</option>
								<option value="1">January</option>
								<option value="2">February</option>
								<option value="3">March</option>
								<option value="4">April</option>
								<option value="5">May</option>
								<option value="6">June</option>
								<option value="7">July</option>
								<option value="8">August</option>
								<option value="9">September</option>
								<option value="10">October</option>
								<option value="11">November</option>
								<option value="12">December</option>
							</select>
						</div>
						<div class="span4">
							Days of Week<br>
							<select name="schedule_days_of_week" id="schedule_days_of_week" size="5" class="input-medium">
								<option value="*" selected="selected">All Values(*)</option>
								<option value="?">No Specific Value(?)</option>
								<option value="1">Sunday</option>
								<option value="2">Monday</option>
								<option value="3">Tuesday</option>
								<option value="4">Wednesday</option>
								<option value="5">Thursday</option>
								<option value="6">Friday</option>
								<option value="7">Saturday</option>
							</select>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br>
		<div class="row-fluid">
		 	<p>The '*' character is used to specify all values. For example, "*" in the minute field means "every minute". </p>
			<p>The '?' character is allowed for the day-of-month and day-of-week fields. It is used to specify 'no specific value'. This is useful when you need to specify something in one of the two fileds, but not the other. See the examples below for clarification. </p>
		</div>

		<div class="row-fluid">
			<input type="submit" name="schedule_add" value="Save Schedule">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Click Save to save schedule settings.
		</div>



	</form>

	<br>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb-dest">
	  <tr>
	    <th scope="col"><input name="schedules_checkall" id="schedules_checkall" type="checkbox" value="" /></th>
	    <th scope="col"><a href="#">Name<span></span></a></th>
	    <th scope="col"><a href="#">Schedule<span></span></a></th>
	  </tr>
	  <tbody id='schedules_container'>
	  </tbody>
	</table>

	<div class="del-wrap">
	  <input id="delete_schedules_btn" type="button" name="delete" value="   Delete   " /><span></span>
	</div>
</div>

<script type="text/javascript" language="javascript">
	$(document).ready(function(){
		$('#new_schedule_btn').click(function(){
			$('#scheduleForm').slideDown();
		});
		$('#scheduleForm').submit(function(e){
			e.preventDefault();
			var minutes = $('#schedule_minutes').val();
			if ($('#schedule_every_minute').prop('checked') && minutes != '*') {
				minutes = '0/' + minutes;
			}
			var schedule = {
				'name' : $('#schedule_name').val(),
				'minutes' : minutes,
				'hours' : $('#schedule_hours').val(),
				'daysOfWeek' : $('#schedule_days_of_week').val(),
				'daysOfMonth' : $('#schedule_days_of_month').val()
			};

			/* TODO: Do something with this result. */
			console.log(schedule);


			return false;
		});
	});
</script>
