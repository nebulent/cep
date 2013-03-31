<script id="monitorsTemplate" type="text/x-jquery-tmpl">
    <tr class="destinationRowMonitor" id="${'$'}{ider}">
        <td><input name="checkrow" type="checkbox" class="monitor_check" id="${'$'}{ider}" /></td>
        <td id="name"><a id="" href="" onclick="return editMonitor('${"$"}{ider}')">${'$'}{name}</a></td>
        <td id="message">${'$'}{message}</td>
        <td id="criticality">${'$'}{criticality}</td>
    </tr>
</script>

<script id="parametersTemplate" type="text/x-jquery-tmpl">
    <tr class="destinationRowParameter" id="${'$'}{ider}">
        <td><input name="checkrow" type="checkbox" class="parameter_check" id="${'$'}{ider}" /></td>
        <td id="id"><a id="" href="" onclick="return editParameter('${"$"}{ider}')">${'$'}{id_label}</a></td>
        <td id="comparator">${'$'}{comparator_label}</td>
        <td id="value">${'$'}{value}</td>
    </tr>
</script>

<script id="selectResourcesTemplate" type="text/x-jquery-tmpl">
<option value="${'$'}{id}">${'$'}{label}</option>
</script>

<script id="selectConditionsTemplate" type="text/x-jquery-tmpl">
	<option value="${'$'}{id}">${'$'}{label}</option>
</script>

<script id="selectParametersTemplate" type="text/x-jquery-tmpl">
	<option value="${'$'}{id}">${'$'}{label}</option>
</script>

<script id="selectComparatorsTemplate" type="text/x-jquery-tmpl">
	<option value="${'$'}{code}">${'$'}{label}</option>
</script>
						
<script type="text/javascript" language="javascript">
var monitorsContainer;
var editingMonitor;
var editingMonitorIder;
var editingParameterIder;

var mIder = 0;

var selectedMonitors = [
	<@s.iterator value="monitoring.monitors" status="rowStatus">
	{
		'ider' : -1,
		'id' : '<@s.property value="id"/>',
		'name' : '<@s.property value="name"/>',
		'message' : '<@s.property value="message"/>',
		'resource' : '<@s.property value="resourceCode"/>',
		'condition' : '<@s.property value="conditionCode"/>',
		'criticality' : '<@s.property value="criticalityTypeCode"/>',
		'occurrenceInterval' : '<@s.property value="occurrenceInterval"/>',
		'managedQuery' : '<@s.property value="managedQuery"/>',
		'schedules' : [
			<@s.iterator value="schedules" status="rowStatusSchedules">
			{
				'ider' : -1,
				'id' : '<@s.property value="id"/>',
				'name' : '<@s.property value="name"/>',
				'minutes' : '<@s.property value="minutes"/>',
				'hours' : '<@s.property value="hours"/>',
				'daysOfWeek' : '<@s.property value="daysOfWeek"/>',
				'daysOfMonth' : '<@s.property value="daysOfMonth"/>',
				'months' : '<@s.property value="months"/>',
				'seconds' : '<@s.property value="seconds"/>'
			}<@s.if test="!#rowStatusSchedules.last">,</@s.if>
			</@s.iterator>
		],
		'conditions' : [
			<@s.iterator value="conditions" status="rowStatusConditions">
			{
				'ider' : -1,
				'id' : '<@s.property value="id"/>',
				'name' : '<@s.property value="name"/>',
				'expression' : '<@s.property value="expression"/>'
			}<@s.if test="!#rowStatusConditions.last">,</@s.if>
			</@s.iterator>
		],
		'parameters' : [
			<@s.iterator value="monitoring.getMonitorParameters(id)" status="rowStatusParams">
			{
				'ider' : -1,
				'id' : '<@s.property value="id"/>',
				'comparator' : '<@s.property value="comparator"/>',
				'value' : '<@s.property value="value"/>'
			}<@s.if test="!#rowStatusParams.last">,</@s.if>
			</@s.iterator>
		]
	}<@s.if test="!#rowStatus.last">,</@s.if>
	</@s.iterator>
];

var deletedMonitors = [];

var conditions = [];

var resources = [
    <@s.iterator value="monitoring.monitoredResources" status="rowStatusResources">
	{
		'id' : '<@s.property value="id"/>',
		'label' : '<@s.property value="label"/>',
		'description' : '<@s.property value="description"/>',
		'supportedConditions' : [
			<@s.iterator value="supportedConditions" status="rowStatusSupConditions">
			{
				'id' : '<@s.property value="id"/>',
				'label' : '<@s.property value="label"/>',
				'parameters' : [
			  		<@s.iterator value="supportedParameters" status="rowStatusSupParams">
					{
						'id' : '<@s.property value="id"/>',
						'label' : '<@s.property value="label"/>',
		    			'datatype' : '<@s.property value="datatype"/>',
		    			'required' : '<@s.property value="required"/>',
		    			'defaultValue' : '<@s.property value="defaultValue"/>',
		    			'comparators' : [
					  		<@s.iterator value="supportedComparators" status="rowStatusComparators">
							{
								'code' : '<@s.property value="code"/>',
								'label' : '<@s.property value="label"/>'
							}<@s.if test="!#rowStatusComparators.last">,</@s.if>
							</@s.iterator>
					    ]
					}<@s.if test="!#rowStatusSupParams.last">,</@s.if>
					</@s.iterator>
			    ],
			    'outParameters' : [
			    	<@s.iterator value="outParameters" status="rowStatusOutParams">
					{
						'id' : '<@s.property value="id"/>',
						'label' : '<@s.property value="label"/>',
						'description' : '<@s.property value="description"/>',
		    			'datatype' : '<@s.property value="datatype"/>',
		    			'required' : '<@s.property value="required"/>',
		    			'defaultValue' : '<@s.property value="defaultValue"/>',
		    			'comparators' : [
					  		<@s.iterator value="supportedComparators" status="rowStatusComparators">
							{
								'code' : '<@s.property value="code"/>',
								'label' : '<@s.property value="label"/>'
							}<@s.if test="!#rowStatusComparators.last">,</@s.if>
							</@s.iterator>
					    ]
					}<@s.if test="!#rowStatusOutParams.last">,</@s.if>
					</@s.iterator>
			    ],
			    'expression' : '<@s.property value="monitoring.escape(expression.trim())" escape="true"/>'
			}<@s.if test="!#rowStatusSupConditions.last">,</@s.if>
			</@s.iterator>
		],
		'managedConditions' : [
			<@s.iterator value="supportedConditions" status="rowStatusManConditions">
			{
				'id' : '<@s.property value="id"/>',
				'label' : '<@s.property value="label"/>',
				'parameters' : [
			  		<@s.iterator value="supportedParameters" status="rowStatusSupParams">
					{
						'id' : '<@s.property value="id"/>',
						'label' : '<@s.property value="label"/>',
		    			'datatype' : '<@s.property value="datatype"/>',
		    			'required' : '<@s.property value="required"/>',
		    			'defaultValue' : '<@s.property value="defaultValue"/>',
		    			'comparators' : [
					  		<@s.iterator value="supportedComparators" status="rowStatusComparators">
							{
								'code' : '<@s.property value="code"/>',
								'label' : '<@s.property value="label"/>'
							}<@s.if test="!#rowStatusComparators.last">,</@s.if>
							</@s.iterator>
					    ]
					}<@s.if test="!#rowStatusSupParams.last">,</@s.if>
					</@s.iterator>
			    ],
			    'outParameters' : [
			    	<@s.iterator value="outParameters" status="rowStatusOutParams">
					{
						'id' : '<@s.property value="id"/>',
						'label' : '<@s.property value="label"/>',
						'description' : '<@s.property value="description"/>',
		    			'datatype' : '<@s.property value="datatype"/>',
		    			'required' : '<@s.property value="required"/>',
		    			'defaultValue' : '<@s.property value="defaultValue"/>',
		    			'comparators' : [
					  		<@s.iterator value="supportedComparators" status="rowStatusComparators">
							{
								'code' : '<@s.property value="code"/>',
								'label' : '<@s.property value="label"/>'
							}<@s.if test="!#rowStatusComparators.last">,</@s.if>
							</@s.iterator>
					    ]
					}<@s.if test="!#rowStatusOutParams.last">,</@s.if>
					</@s.iterator>
			    ]
			}<@s.if test="!#rowStatusManConditions.last">,</@s.if>
			</@s.iterator>
		]
	}<@s.if test="!#rowStatusResources.last">,</@s.if>
	</@s.iterator>
];

function addMonitors(){
	var form = $("form.#monitorsForm");

    var item;
    var item2;
    var index = 0;
    var index2;
    for(item in selectedMonitors){
		form.append("<input type='hidden' name='monitoring.monitors[" + index + "].id' value='" + selectedMonitors[item].id + "' />");
		form.append("<input type='hidden' name='monitoring.monitors[" + index + "].name' value='" + selectedMonitors[item].name + "' />");
		form.append("<input type='hidden' name='monitoring.monitors[" + index + "].message' value='" + selectedMonitors[item].message + "' />");
		form.append("<input type='hidden' name='monitoring.monitors[" + index + "].criticalityTypeCode' value='" + selectedMonitors[item].criticality + "' />");
		form.append("<input type='hidden' name='monitoring.monitors[" + index + "].resourceCode' value='" + selectedMonitors[item].resource + "' />");
		form.append("<input type='hidden' name='monitoring.monitors[" + index + "].occurrenceInterval' value='" + selectedMonitors[item].occurrenceInterval + "' />");
		form.append("<input type='hidden' name='monitoring.monitors[" + index + "].conditionCode' value='" + selectedMonitors[item].condition + "' />");

		index2 = 0;
		for(item2 in selectedMonitors[item].parameters){
			form.append("<input type='hidden' name='monitoring.monitors[" + index + "].monitorParameters.monitorParameters[" + index2 + "].id' value='" + selectedMonitors[item].parameters[item2].id + "' />");
			form.append("<input type='hidden' name='monitoring.monitors[" + index + "].monitorParameters.monitorParameters[" + index2 + "].comparator' value='" + selectedMonitors[item].parameters[item2].comparator + "' />");
			form.append("<input type='hidden' name='monitoring.monitors[" + index + "].monitorParameters.monitorParameters[" + index2 + "].value' value='" + selectedMonitors[item].parameters[item2].value + "' />");
			index2++;
		}
		
        index++;
    }

	index = 0;
    for(item in deletedMonitors){
    	form.append("<input type='hidden' name='monitoring.deletedMonitors[" + index + "]' value='" + deletedMonitors[item] + "' />");
    	index++;
    }
    return true;
}

function getParameterLabel(condition_id, parameter) {
	var parameter_label = {
		'ider' : parameter.ider,
		'id_label' : parameter.id,
		'comparator_label' : parameter.comparator,
		'value' : parameter.value
	};
	
	for (item in conditions) {
		if (conditions[item].id == condition_id) {
			for (item2 in conditions[item].parameters) {
				if (conditions[item].parameters[item2].id == parameter.id) {
					parameter_label.id_label = conditions[item].parameters[item2].label;
					
					for (item3 in conditions[item].parameters[item2].comparators) {
						if (conditions[item].parameters[item2].comparators[item3].code == parameter.comparator) {
							parameter_label.comparator_label = conditions[item].parameters[item2].comparators[item3].label;
						}
					}
				}
			}
		}
	}
	
	return parameter_label;
}

function getParameterType(condition_id, parameter_id) {
	for (item in conditions) {
		if (conditions[item].id == condition_id) {
			for (item2 in conditions[item].parameters) {
				if (conditions[item].parameters[item2].id == parameter_id) {
					return conditions[item].parameters[item2].datatype;
				}
			}
		}
	}
	
	return "";
}

function setSelect(id, value){
	if (value == "") {
		var firstElement = $("#"+id).children().first().val();
		$("#"+id).val(firstElement);
	}
	else {
		$("#"+id).val(value);
	}
}

function checkResources(){
	var resource = $("#monitorResources").val();
	
	for (item in resources) {
		if (resources[item].id == resource) {
			var conditionsContainer = $("#monitorConditions");
			conditionsContainer.empty();
			
			conditions = resources[item].supportedConditions;
			for(item in conditions){
				conditionsContainer.append('#selectConditionsTemplate', conditions[item], null);
			}
			checkConditions();
		}
	}
}

function checkConditions(){
	var condition = $("#monitorConditions").val();
	
	for (item in conditions) {
		if (conditions[item].id == condition) {
			var parametersContainer = $("#parameterParameters");
			parametersContainer.empty();
			for(item2 in conditions[item].parameters){
				parametersContainer.append('#selectParametersTemplate', conditions[item].parameters[item2], null);
			}
			checkParameters();
		}
	}
}

function checkParameters(){
	var condition = $("#monitorConditions").val();
	var parameter = $("#parameterParameters").val();
	
	for (item in conditions) {
		if (conditions[item].id == condition) {
			$("#monitorConditionExpression").val(conditions[item].expression);
			
			for (item2 in conditions[item].parameters) {
				if (conditions[item].parameters[item2].id == parameter) {
					comparatorsContainer = $("#parameterComparators");
					comparatorsContainer.empty();
					for(item3 in conditions[item].parameters[item2].comparators){
						comparatorsContainer.append('#selectComparatorsTemplate', conditions[item].parameters[item2].comparators[item3], null);
					}
				}
			}
		}
	}
}

function newMonitor() {
	editingMonitor = {
		'ider' : -1,
		'name' : '',
		'id' : '',
		'message' : '',
		'criticality' : '',
		'occurrenceInterval' : '',
		'resource' : '',
		'condition' : '',
		'expression' : '',
		'conditions': [],
		'parameters' : []
	};

	editingMonitor.resource = resources[0].id;
	editingMonitor.condition = resources[0].supportedConditions[0].id;
	editingMonitor.parameters = resources[0].supportedConditions[0].parameters;
	
	conditions = resources[0].supportedConditions;
	
	for (item in conditions) {
		//alert(conditions[item].expression);
		if (conditions[item].id == editingMonitor.condition) {
			editingMonitor.expression = conditions[item].expression;
			editingMonitor.conditions[0] = conditions[item];
		}
	}
	
	populateMonitorFields();
}

function populateMonitorFields() {
	$("#monitorName").val(editingMonitor.name);
	//$("#monitorSchedule").val(editingMonitor.scheduleUuid);
 	$("#monitorMessage").val(editingMonitor.message);
 	setSelect("monitorCriticality", editingMonitor.criticality);
 	$("#monitorOccurrenceInterval").val(editingMonitor.occurrenceInterval);
 	
 	for (item in conditions) {
		//alert(conditions[item].expression);
		if (conditions[item].id == editingMonitor.condition) {
			editingMonitor.expression = conditions[item].expression;
			editingMonitor.conditions[0] = conditions[item];
		}
	}
 	
 	$("#monitorConditionExpression").val(editingMonitor.conditions[0].expression);
 	
 	setSelect("monitorResources", editingMonitor.resource);
 	setSelect("monitorConditions", editingMonitor.condition);
 		
	var parametersContainer = $('#parameters_container');
    parametersContainer.empty();
	for(item in editingMonitor.parameters){
		parametersContainer.append('#parametersTemplate', getParameterLabel(editingMonitor.condition, editingMonitor.parameters[item]), null);	
	}
}

function populateMonitor() {
	editingMonitor.name = $("#monitorName").val();
	//editingMonitor.scheduleUuid = $("#monitorSchedule").val();
	editingMonitor.message = $("#monitorMessage").val();
	editingMonitor.criticalityTypeCode = $("#monitorCriticality").val();
	editingMonitor.occurrenceInterval = $("#monitorOccurrenceInterval").val();
	editingMonitor.resource = $("#monitorResources").val();
	editingMonitor.condition = $("#monitorConditions").val();
}

function addEditMonitor(mode) {
	populateMonitor();
	
	var error_flag = false;
	if (validateField(editingMonitor.name, "monitorNameErrorLabel", true, false, false, false, 0, 0) == false)
	{
		error_flag = true;
	}
	if (error_flag == true) return;

	if (mode == 'new') {
		editingMonitor.ider = mIder;
		mIder++;
		
		for(item in selectedMonitors) {
			if (selectedMonitors[item].name == editingMonitor.name)
			{
				alert("This monitor name is already present.");
				return false;
			}
		}

		$('#monitors_container').append('#monitorsTemplate', editingMonitor, null);
		selectedMonitors[selectedMonitors.length] = editingMonitor;
	}
	else {
		editingMonitor.ider = editingMonitorIder;

		for(item in selectedMonitors) {
			if (selectedMonitors[item].ider != editingMonitorIder) {
				if (selectedMonitors[item].name == editingMonitor.name)
				{
					alert("This monitor name is already present.");
					return false;
				}
			}
		}
		
		$(".destinationRowMonitor").each(function(index) {
    		var ider = $(this).attr('id');

			if (ider == editingMonitorIder) {
				$(this).children("#name").children().text(editingMonitor.name);
				$(this).children("#message").text(editingMonitor.message);
				$(this).children("#criticality").text(editingMonitor.criticalityTypeCode);
			}
		});
		
		for(item in selectedMonitors) {
			if (selectedMonitors[item].ider == editingMonitorIder){
				selectedMonitors[item] = editingMonitor;
				break;
			}
		}
	}

	return true;
}

function editMonitor(ider) {
	$("#monitorForm").hide();

	for(item in selectedMonitors) {
		if (selectedMonitors[item].ider == ider) {
			editingMonitorIder = ider;
			editingMonitor = selectedMonitors[item];
			
			populateMonitorFields();
			
			$("#add_monitor_btn").attr('class', 'edit');
			$("#monitorForm").show("slow");
			
			break;
		}
	}

	return false;   //no redirect
}

function addEditParameter(mode) {
	var parameter = {
		'ider' : -1,
		'id' : '',
		'comparator' : '',
		'value' : ''
	};

	parameter.id = $("#parameterParameters").val();
	parameter.comparator = $("#parameterComparators").val();
	parameter.value = $("#parameterValue").val();

	var error_flag = false;
	var parameter_type = getParameterType(editingMonitor.condition, parameter.id);
	
	if (parameter_type == 'STRING') {
		if (validateField(parameter.value, "parameterValueErrorLabel", true, false, false, false, 0, 0) == false)
		{
			error_flag = true;
		}
	}
	else if (parameter_type == 'INTEGER') {
		if (validateField(parameter.value, "parameterValueErrorLabel", true, true, false, false, 0, 0) == false)
		{
			error_flag = true;
		}
	}
	else if (parameter_type == 'FLOAT') {
		if (validateField(parameter.value, "parameterValueErrorLabel", true, false, false, false, 0, 0) == false)
		{
			error_flag = true;
		}
		else
		if (/^([+/-]?((([0-9]+(\.)?)|([0-9]*\.[0-9]+))([eE][+\-]?[0-9]+)?))$/.test(parameter.value) == false) {
			$("#parameterValueErrorLabel").text("Invalid double number.").show();
			error_flag = true;
		}
	}

	if (error_flag == true) return;

	if (mode == 'new') {
		parameter.ider = mIder;
		mIder++;
		
		/*for(item in selectedMonitors) {
			if (selectedMonitors[item].name == monitor.name)
			{
				alert("This monitor name is present yet.");
				return false;
			}
		}*/

		$('#parameters_container').append('#parametersTemplate', getParameterLabel(editingMonitor.condition, parameter), null);
		editingMonitor.parameters[editingMonitor.parameters.length] = parameter;
	}
	else {
		parameter.ider = editingParameterIder;

		/*for(item in selectedMonitors) {
			if (selectedMonitors[item].ider != editingMonitorIder) {
				if (selectedMonitors[item].name == monitor.name)
				{
					alert("This monitor name is already present.");
					return false;
				}
			}
		}*/
		
		$(".destinationRowParameter").each(function(index) {
    		var ider = $(this).attr('id');

			if (ider == editingParameterIder) {
				var parameter_label = getParameterLabel(editingMonitor.condition, parameter);
				
				$(this).children("#id").children().text(parameter_label.id_label);
				$(this).children("#comparator").text(parameter_label.comparator_label);
				$(this).children("#value").text(parameter.value);
			}
		});
		
		for(item in editingMonitor.parameters) {
			if (editingMonitor.parameters[item].ider == editingParameterIder){
				editingMonitor.parameters[item] = parameter;
				break;
			}
		}
	}

	return true;
}

function editParameter(ider) {
	$("#parameterForm").hide();

	for(item in editingMonitor.parameters) {
		if (editingMonitor.parameters[item].ider == ider) {
			editingParameterIder = ider;

			$("#parameterParameters").val(editingMonitor.parameters[item].id);
   		 	$("#parameterComparators").val(editingMonitor.parameters[item].comparator);
    		$("#parameterValue").val(editingMonitor.parameters[item].value);
			
			$("#add_parameter_btn").attr('class', 'edit');
			$("#parameterForm").show("slow");
			
			break;
		}
	}

	return false;   //no redirect
}

function validateField(val, errorField, isReqiered, isNumeric, isMin, isMax, minimum, maximum) {
	$("#"+errorField).text("").empty();

	if (isReqiered == true) {
		if (val == "") {
			$("#"+errorField).text("This field is required.").show();
			return false;
		}
	}

	if (isNumeric == true) {
		if (val == "") return true;
		
		if (/^[-+]?\d+$/.test(val) == false) {
			$("#"+errorField).text("Please, enter only digits.").show();
			return false;
		}
		else
		{
			if (isMin == true) {
				if (val < minimum) {
					if (minimum == 0) {
						$("#"+errorField).text("Please, enter only non-negative number.").show();
					}
					else
					if (minimum == 1) {
						$("#"+errorField).text("Please, enter only positive number.").show();
					}
					else {
						$("#"+errorField).text("Minimum value is "+minimum+".").show();
					}
					return false;
				}
			}
			if (isMax == true) {
				if (val > maximum) {
					$("#"+errorField).text("Maximum value is "+maximum+".").show();
					return false;
				}
			}
		}
	}

	return true;
}

$(document).ready(function(){

	selectedMonitorsContainer = $('#monitors_container');

	// Populate table initially.
	for(item in selectedMonitors){
		selectedMonitors[item].ider = mIder;
		mIder++;
		
		for(item2 in selectedMonitors[item].parameters){
			selectedMonitors[item].parameters[item2].ider = mIder;
			mIder++;
		}

		selectedMonitorsContainer.append('#monitorsTemplate', selectedMonitors[item], null);
	}

	/*
	for(item in conditions){
		$("#monitorConditions").append('#selectConditionsTemplate', conditions[item], null);
	}
	checkConditions();
	 */
	for(item in resources){
		$("#monitorResources").append('#selectResourcesTemplate', resources[item], null);
	}
	checkResources();
	
	$("#new_monitor_btn").click( function() {
		$("#monitorForm").hide();
		$("#parameterForm").hide();
		newMonitor();
	    $("#add_monitor_btn").attr('class', 'new');
    	$("#monitorForm").show("slow");
    });

    $("#add_monitor_btn").click( function() {
    	var mode = $("#add_monitor_btn").attr('class');
    	if (addEditMonitor(mode) == true) {
    		$("#monitorForm").hide();
		}
    });

	$("#new_parameter_btn").click( function() {
		setSelect("parameterParameters", "");
		setSelect("parameterComparators", "");
    	$("#parameterValue").val("");
    	
	    $("#add_parameter_btn").attr('class', 'new');
    	$("#parameterForm").show("slow");
    });
    
    $("#add_parameter_btn").click( function() {
    	var mode = $("#add_parameter_btn").attr('class');
    	if (addEditParameter(mode) == true) {
    		$("#parameterForm").hide();
		}
    });

	$("#monitors_checkall").click( function() {
        if($('#monitors_checkall').attr('checked')){
            $('.monitor_check').attr('checked', true);
        } else {
            $('.monitor_check').attr('checked', false);
        }
    });
    
    $("#parameters_checkall").click( function() {
        if($('#parameters_checkall').attr('checked')){
            $('.parameter_check').attr('checked', true);
        } else {
            $('.parameter_check').attr('checked', false);
        }
    });

    $("#delete_monitors_btn").click( function() {
    	$(".monitor_check").each(function(index) {
    		if ($(this).attr('checked')) {
				var ider = $(this).parent().parent(".destinationRowMonitor").attr('id');

				var index = 0;
				for(item in selectedMonitors) {
					if (selectedMonitors[item].ider == ider) {
						if (editingMonitorIder == ider) {
							$("#monitorForm").hide();
						}
						
						$(this).parent().parent(".destinationRowMonitor").hide('medium', function() {
							$(this).parent().parent(".destinationRowMonitor").empty();
					    	$(this).parent().parent(".destinationRowMonitor").remove();
					  	});
  	
  						if ((selectedMonitors[item].uuid != undefined)&&
  							(selectedMonitors[item].uuid.length > 0)) {
  							deletedMonitors[deletedMonitors.length] = selectedMonitors[item].uuid;
  						}
  						
						delete selectedMonitors[item];
						selectedMonitors.splice(index, 1);
						break;
					}
				
					index++;
				}
			}
		});

		$('#monitors_checkall').attr('checked', false);
    });

	$("#delete_parameters_btn").click( function() {
    	$(".parameter_check").each(function(index) {
    		if ($(this).attr('checked')) {
				var ider = $(this).parent().parent(".destinationRowParameter").attr('id');

				var index = 0;
				for(item in editingMonitor.parameters) {
					if (editingMonitor.parameters[item].ider == ider) {
						if (editingParameterIder == ider) {
							$("#parameterForm").hide();
						}
						
						$(this).parent().parent(".destinationRowParameter").hide('medium', function() {
							$(this).parent().parent(".destinationRowParameter").empty();
					    	$(this).parent().parent(".destinationRowParameter").remove();
					  	});
  	
						delete editingMonitor.parameters[item];
						editingMonitor.parameters.splice(index, 1);
						break;
					}
				
					index++;
				}
			}
		});

		$('#parameters_checkall').attr('checked', false);
    });

    $("#monitorsForm").submit( function() {
		addMonitors();
		return true;
	});
});
</script>

<style type="text/css">
	label.error { color: red; font-size:14px; padding-left:10px; }
	span.error { color: red; font-size:14px; padding-left:10px; }
</style>

<div class="content" id="monitors">
	<form id="monitorsForm" action="${base}<@s.property value='controllerNamespace'/>!updateMonitors" method="POST">
		<@s.hidden name="model.uuid"/>
		
		<div class="divider"><span>Monitors</span></div>
		
		<div class="fldcont wmarg clear">
			<input id="new_monitor_btn" type="button" name="new" value=" New Monitor " class=""/>
		</div>

		<div id="monitorForm" style="display:none; ">
			<div class="subblock">
				<div class="flditem clear">
					<label class="fldtitle fl_left" for="monitorName">Name</label>
					<div class="fl_left fldcont">
						<@s.textfield id="monitorName" cssClass="formTextField" size="40" maxlength="100"/>
						<label id="monitorNameErrorLabel" for="monitorName" generated="true" class="error"></label>
						<span class="descr"></span>
					</div>
				</div>
				<div class="flditem clear">
					<label class="fldtitle fl_left" for="hitnum">Schedule</label>
					<div class="fl_left fldcont">
						<@s.select id="monitorSchedule" list="schedules" listKey="displayUUID" listValue="displayTitle" multiple="false" />
			        </div>
		        </div>
				<div class="flditem clear">
					<label class="fldtitle fl_left" for="hitnum">Criticality</label>
					<div class="fl_left fldcont">
						<@s.select id="monitorCriticality" list="monitoring.criticalities" multiple="false" cssClass="required"/>
			        </div>
		        </div>
		        <div class="flditem clear">
					<label class="fldtitle fl_left" for="monitorOccurrenceInterval">Occurrence Interval</label>
					<div class="fl_left fldcont">
						<@s.textfield id="monitorOccurrenceInterval" cssClass="formTextField" size="20" maxlength="30"/>
						<label id="monitorOccurrenceIntervalErrorLabel" for="monitorOccurrenceInterval" generated="true" class="error"></label>
						<span class="descr"></span>
					</div>
				</div>
				
				<div class="flditem clear">
					<label class="fldtitle fl_left" for="hitnum">Resource</label>
					<div class="fl_left fldcont">
						<@s.select id="monitorResources" onchange="checkResources();" list="" multiple="false" cssClass="required"/>
			        </div>
		        </div>
		        
		        <div class="flditem clear">
					<label class="fldtitle fl_left" for="hitnum">Condition</label>
					<div class="fl_left fldcont">
						<@s.select id="monitorConditions" onchange="checkConditions();" list="" multiple="false" cssClass="required"/>
			        </div>
		        </div>
		        
		        <div class="divider"><span>Esper Expression</span></div>
				<div class="subblock">
			        <div class="flditem clear">
						<label class="fldtitle fl_left" for="monitorConditionExpression">Esper Expression</label>
						<div class="fl_left fldcont">
							<@s.textarea id="monitorConditionExpression" cols="80" rows="10" cssClass="monitorMessage"/>
							<label id="monitorConditionExpressionErrorLabel" for="monitorConditionExpression" generated="true" class="error"></label>
							<span class="descr"></span>
						</div>
					</div>
				</div>
				
		        <div class="divider"><span>Parameters</span></div>
		        <div class="subblock">
			        <div class="flditem clear">
						<input id="new_parameter_btn" type="button" value=" New Parameter " class=""/>
					</div>
					
					<div id="parameterForm" style="display:none; ">
						<div class="subblock">
							<div class="flditem clear">
								<label class="fldtitle fl_left" for="hitnum">Parameter</label>
								<div class="fl_left fldcont">
									<@s.select id="parameterParameters" onchange="checkParameters();" list="" multiple="false" cssClass="required"/>
						        </div>
					        </div>
					        <div class="flditem clear">
								<label class="fldtitle fl_left" for="hitnum">Comparator</label>
								<div class="fl_left fldcont">
									<@s.select id="parameterComparators" list="" multiple="false" cssClass="required"/>
						        </div>
					        </div>
					        <div class="flditem clear">
								<label class="fldtitle fl_left" for="parameterValue">Value</label>
								<div class="fl_left fldcont">
									<@s.textfield id="parameterValue" cssClass="formTextField" size="20" maxlength="30"/>
									<label id="parameterValueErrorLabel" for="parameterValue" generated="true" class="error"></label>
									<span class="descr"></span>
								</div>
							</div>
							<div class="fldcont wmarg clear">
					        	<input id="add_parameter_btn" type="button" value="   Ok   " class=""/>
					        </div>
						</div>
					</div>
					
					<table width="80%" border="0" cellspacing="0" cellpadding="0" class="tb-dest">
			            <tr>
			                <th scope="col"><input id="parameters_checkall" type="checkbox" value="" /></th>
			                <th scope="col"><a href="#">Parameter<span></span></a></th>
			                <th scope="col"><a href="#">Comparator<span></span></a></th>
			                <th scope="col"><a href="#">Value<span></span></a></th>
			            </tr>
			            <tbody id='parameters_container'>
			            </tbody>
			        </table>
	
			        <div class="del-wrap">
			    	    <input id="delete_parameters_btn" type="button" value="   Delete   " /><span></span>
			   	 	</div>
				</div>

				<div class="divider"><span>Message</span></div>
				<div class="subblock">
			        <div class="flditem clear">
						<label class="fldtitle fl_left" for="monitorMessage">Message</label>
						<div class="fl_left fldcont">
							<@s.textarea id="monitorMessage" cols="80" rows="10" cssClass="monitorMessage"/>
							<label id="monitorMessageErrorLabel" for="monitorMessage" generated="true" class="error"></label>
							<span class="descr"></span>
						</div>
					</div>
				</div>
					
		        <div class="fldcont wmarg clear">
		        	<input id="add_monitor_btn" type="button" name="add" value="   Ok   " class=""/>
		        </div>
			</div>
		</div>

		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb-dest">
            <tr>
                <th scope="col"><input name="monitors_checkall" id="monitors_checkall" type="checkbox" value="" /></th>
                <th scope="col"><a href="#">Name<span></span></a></th>
                <th scope="col"><a href="#">Message<span></span></a></th>
                <th scope="col"><a href="#">Criticality<span></span></a></th>
            </tr>
            <tbody id='monitors_container'>
            </tbody>
        </table>
        
        <div class="del-wrap">
    	    <input id="delete_monitors_btn" type="button" name="delete" value="   Delete   " /><span></span>
   	 	</div>
   	 	
   	 	<div class="divider"></div>
		<div class="btnwrap clear">
		    <input type="submit" class="formButton" value="Save" name="btnSave" />                        
		</div>
	</form>
</div>
