/* Data tables */
$(document).ready(function() {
	$('#table_order').DataTable({
		"scrollX" : true,
		"paging": false,
		"info": false,
	    "pageLength": 1,\
		"order" : [],
		"columnDefs" : [ {
			"targets" : 'no-sort',
			"orderable" : false,
		} ],
		dom : "Bfrtip",
		buttons : [ /*'pageLength',*/ {
			extend : 'csvHtml5',
			title : 'order_list',
			className : 'btn btn-primary'
		}, {
			extend : 'excelHtml5',
			title : 'Data export',
			className : 'btn btn-primary'
		}, {
			extend : 'print',
			className : 'btn btn-primary'
		}, {
			extend : 'copy',
			className : 'btn btn-primary'
		} ]
	});

	$('#table_user').DataTable({
		"scrollX" : true,
		"order" : [],
		"columnDefs" : [ {
			"targets" : 'no-sort',
			"orderable" : false,
		} ],
		dom : "Bfrtip",
		buttons : [ 'pageLength', {
			extend : 'csvHtml5',
			title : 'order_list'
		}, {
			extend : 'excelHtml5',
			title : 'Data export'
		}, 'copy', 'print' ]
	});

	// Wizard
	// $('.nav a').not('.active').addClass('disabled');
	// $('.nav a').not('.active').find('a').removeAttr("data-toggle");

	// toast
	$('.toast').toast('show');

	/* Date format */

	/*$('.datepicker').datepicker({
		format : 'dd/mm/yyyy',
		autoclose : true,
		todayHighlight : true,
		endDate : "today"
	});*/

	$('[data-toggle="tooltip"]').tooltip();
	
	/* custom search */

	oTable = $('#table_order').DataTable();
	$('#search-inp').keyup(function(){
	      oTable.search($(this).val()).draw() ;
	})


});


function validateDate(src, compareWith) {
	console.log("--------------src.value::"+src.value);
	console.log("--------------compareWith::"+compareWith);
	var srcDate = false,d1,d2;
	if(src && src.value && src.value !="" && src.value.length > 0) {
		console.log("--------------src.value::"+src.value);
		srcDate = src.value;
		srcDate = srcDate.split("/");//.join("-");//srcDate.replaceAll("/","-");
		d1 = new Date(srcDate[2],srcDate[1],srcDate[0]);
	}
	if(compareWith && compareWith.length >0) {
		compareWith = compareWith;
		compareWith = compareWith.split("/");//.join("-");//compareWith.replaceAll("/","-");
		d2 = new Date(compareWith[2],compareWith[1],compareWith[0]);
	}
	
	console.log("--------------d1::"+d1);
	console.log("--------------d2::"+d2);
	console.log("--------------d1 >= d2::"+ (d1 >= d2));
	if(d1 >= d2) {
		return false;
	} else {
		return true;
	}
}


/* language */

function onLanguageClick(selectedOption) {
	var _ctx = $("meta[name='_ctx']").attr("content");
	var loc = window.location +"";
	console.log('-------loc:::'+loc);
	console.log('-------_ctx:::'+_ctx);
	console.log('-------ln:::'+selectedOption);
	console.log('-------fromLink=search:::'+loc.indexOf("fromLink=search"));
	console.log('-------search--:::'+loc.indexOf("/search"));
	if (selectedOption != '' && loc.indexOf("fromLink=search") !=-1) {
		window.location.replace(_ctx
				+ "/orderdetails?fromLink=search&ln=" + selectedOption);
	} else

	if (selectedOption != '' && loc.indexOf("/search") !=-1) {
		$("#ln").val(selectedOption);
		console.log('-------ln:::'+$("#ln").val());
		var posting = $.post(_ctx
				+ "/orderdetails/search", $(
				'#searchForm').serialize());
	} else if (selectedOption != '') {
		window.location.replace('?ln=' + selectedOption);
	}
}

