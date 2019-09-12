TreeLoader = {
    loadLibTree : function(type,treeEle){
      $.get("/library/tree",{"use":type},function(data,status){
        var treeData = new Array();
        $.each(data,function(index,ele){
            var lib = new Object();
            lib.title = ele.name;
            lib.id = "library-" + ele.id;
            lib.nodeType = "library";
            lib.dataType = ele.type;
            lib.dataId = ele.id;

            if(ele.shelves){
                 var children = new Array();
                 $.each(ele.shelves,function(index,ele){
                    var shelf = new Object();
                    shelf.title = ele.name;
                    shelf.id = "shelf-" + ele.id;
                    shelf.nodeType = "shelf";
                    shelf.dataType = ele.type;
                    shelf.dataId = ele.id;
                    children.push(shelf);
                 });
                 lib.children = children;
            }
            treeData.push(lib);
        });

        //渲染成树
        var treeId = layui.tree.render({
          elem: treeEle,
          data: treeData,
          showCheckbox: true,
          edit: ['add', 'update', 'del'],
          operate: function(obj){
              var operateType = obj.type;
              if(operateType === 'add'){ //增加节点


              } else if(operateType === 'update'){ //修改节点

              } else if(operateType === 'del'){ //删除节点

              };
            }
        });
      });
    }
}