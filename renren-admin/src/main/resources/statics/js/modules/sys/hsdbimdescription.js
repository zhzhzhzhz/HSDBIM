$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/hsdBimDescription/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 10},
            {label: '名称', name: 'name', index: 'name', width: 30},
            {label: '描述', name: 'description', index: 'description', width: 60},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            name: null
        },
        showList: true,
        title: null,
        hsdBim: {},
        resultId:'',
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.hsdBim = {};

        },
        update: function (event) {
            var id = getSelectedRow();
            vm.resultId = id;
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.hsdBim.id != vm.resultId ? "sys/hsdBimDescription/save" : "sys/hsdBimDescription/update";
            var json = editor.txt.getJSON()  // 获取 JSON 格式的内容
            if(json.length>0){
                if(json[0].children[0]==""){
                    vm.hsdBim.description = json[1].children[0]
                }else {
                    vm.hsdBim.description = json[0].children[0]
                }

            }
            if(vm.hsdBim.id == null || vm.hsdBim.id== ""){
                alert("id不能为空")
                return
            }
            if(vm.hsdBim.name == null || vm.hsdBim.name == ""){
                alert("名称不能为空")
                return
            }
            if(vm.hsdBim.description == null || vm.hsdBim.description == ""){
                alert("描述不能为空")
                return
            }
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.hsdBim),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/hsdBimDescription/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/hsdBimDescription/info/" + id, function (r) {
                console.log(r)
                vm.hsdBim = r.hsdBim;
                editor.txt.html('<p>'+vm.hsdBim.description+'</p>')
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            editor.txt.clear()
        }
    },
});
var E = window.wangEditor
var editor = new E('#editor')
editor.create();

