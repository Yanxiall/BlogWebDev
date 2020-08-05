$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/category/list',
        //the datatype which is sent to the server
        datatype: "json",
        colModel: [
            {label: 'categoryId', name: 'categoryId', index: 'categoryId', width: 50, hidden: true, key: true},
            {label: 'categoryName', name: 'categoryName', index: 'categoryName', sortable: false, width: 50},
            {label: 'categoryIcon', name: 'categoryIcon', index: 'categoryIcon', sortable: false, width: 50},
            {label: 'createTime', name: 'createTime', index: 'createTime', sortable: false, width: 60}
        ],
        height: 485,
        rowNum: 5,
        rowList: [5, 10, 15],
        styleUI: 'Bootstrap',
        loadtext: 'loading...',
        rownumbers: true,
        rownumWidth: 35,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        //data which the server sends
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        //names which is sent to the server
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});
