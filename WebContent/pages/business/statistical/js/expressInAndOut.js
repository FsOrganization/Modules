
$(document).ready(function() {
	$('#container').highcharts({
        chart: {
            type: 'column',
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                viewDistance: 25,
                depth: 40
            },
            marginTop: 80,
            marginRight: 40
        },
        title: {
            text: '网点出入件统计'
        },

        xAxis: {
            categories: ['扬州恬苑', '世纪花园', '华诚都汇']
        },
        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: '快件数'
            }
        },
        tooltip: {
            headerFormat: '<b>{point.key}</b><br>',
            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                depth: 40
            }
        },
        series: [{
            name: '收件',
            data: [5, 13, 4],
            stack: ''
        },{
            name: '取件',
            data: [23, 4, 4],
            stack: ''
        },{
            name: '寄件',
            data: [2, 15, 6],
            stack: ''
        },{
            name: '超时件',
            data: [2, 15, 6],
            stack: ''
        }]
    });
	
});
