$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/hsdBimDescription/list',
        datatype: "json",
        colModel: [
            /*{label: 'id', name: 'id', index: 'id', width: 20},*/
            {label: '节点ID', name: 'bid', index: 'bid', width: 30,sortable:false},
            {label: '房间名', name: 'name', index: 'name',  sortable:false,width: 60},
            {label: '所属楼', name: 'bname', index: 'bname',  sortable:false,width: 45},
            {label: '房间号', name: 'title', index: 'title',  sortable:false,width: 45},
            {label: '类型', name: 'type', index: 'type',  sortable:false,width: 45},
            {label: '操作',  edittype:"button",formatter: lookFormat, sortable:false  },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 45,
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
        showMessage:false,
        show: false,
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
            vm.showMessage = true;
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
            vm.showMessage = true;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.hsdBim.id == null ? "sys/hsdBimDescription/save" : "sys/hsdBimDescription/update";
            var formdata=new FormData();
            if(url=="sys/hsdBimDescription/update"){
                formdata.append("id",vm.hsdBim.id);
            }
            var html =editor.txt.html();
            vm.hsdBim.description = vm.HTMLEncode(html);
            if(vm.hsdBim.bid == null || vm.hsdBim.bid== ""){
                alert("节点不能为空")
                return
            }else{
                formdata.append("bid",vm.hsdBim.bid);
            }
            if(vm.hsdBim.name == undefined){
                formdata.append("name","")
            }else{
                formdata.append("name",vm.hsdBim.name)
            }
            if(vm.hsdBim.title == undefined){
                formdata.append("title","")
            }else{
                formdata.append("title",vm.hsdBim.title)
            }
            if(vm.hsdBim.floor == undefined){
                formdata.append("floor","")
            }else{
                formdata.append("floor",vm.hsdBim.floor)
            }

            if(vm.hsdBim.bname == null || vm.hsdBim.bname == ""){
                alert("所属楼不能为空")
                return
            }else {
                formdata.append("bname",vm.hsdBim.bname)
            }
            if(vm.hsdBim.type == null || vm.hsdBim.type == ""){
                alert("类型不能为空")
                return
            }else{
                formdata.append("type",vm.hsdBim.type)
            }
            formdata.append("description",vm.hsdBim.description)
            $.ajax({
                type: "POST",
                url: baseURL + url,
                dataType: 'json',
                contentType: false,
                processData:false,
                data:formdata,
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
        //防止转义
        HTMLEncode : function(html) {
            var temp = document.createElement("div");
            (temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
            var output = temp.innerHTML;
            temp = null;
            return output;
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
                vm.hsdBim = r.hsdBim;
                vm.hsdBim.description = vm.HTMLDecode(vm.hsdBim.description)
                editor.txt.html('<p>'+vm.hsdBim.description+'</p>')
            });
        },
        //反转义
        HTMLDecode : function(text) {
            var temp = document.createElement("div");
            temp.innerHTML = text;
            var output = temp.innerText || temp.textContent;
            temp = null;
            return output;
        },
        reload: function (event) {
            vm.showList = true;
            vm.showMessage = false;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            editor.txt.clear()
        },
        back:function () {
            vm.show = false;
            vm.showList = true;
            vm.hsdBim={},
            editor.txt.clear();
        }
    },
});
var E = window.wangEditor
var editor = new E('#editor')
editor.create();
var F = window.wangEditor
var editor1 = new F('#editor1')
editor1.create();

function lookFormat(cellvalue,options, rowdata) {
    var id=rowdata.id;
    return "<button class='btn btn-primary ' onclick=\"look('" + id + "')\">" +
        "<span style='font-size: 12px'>查看详情</span>" +
        "</button>";
}
function  look(result) {
    var id = result;
    vm.showList = false;
    vm.show = true;
    vm.title = "详情";
    $.get(baseURL + "sys/hsdBimDescription/info/" + id, function (r) {
        vm.hsdBim = r.hsdBim;
        vm.hsdBim.description = vm.HTMLDecode(vm.hsdBim.description)
        editor1.txt.html('<p>'+vm.hsdBim.description+'</p>')

    });
    editor1.$textElem.attr('contenteditable', false)
}

