var TreeLoader = {
    loadLibTree : function(type,treeEle){
      $.get("/library/tree",{"use":type},function(data,status){
        var treeData = new Array();
        $.each(data,function(index,ele){
            var lib = new Object();
            lib.title = ele.name;
            lib.id = "library-" + ele.id;
            lib.type = "library";
            lib.dataId = ele.id;

            if(ele.shelves){
                 var children = new Array();
                 $.each(ele.shelves,function(index,ele){
                    var shelf = new Object();
                    shelf.title = ele.name;
                    shelf.id = "shelf-" + ele.id;
                    shelf.type = "shelf-";
                    shelf.dataId = ele.id;
                    children.push(shelf);
                 });
                 lib.children = children;
            }
            treeData.push(lib);
        });

        //渲染成树
        layui.tree.render({
          elem: treeEle,
          data: treeData,
          showCheckbox: true,
          edit: ['add', 'update', 'del'],
          operate: function(obj){
              var operateStatus = false;
              if(type === 'add'){ //增加节点
                    layui.layer.prompt({
                          title: '索引名称'
                        },
                    function(value, index, elem){
                      $.post("/shelf/tree",
                              {"name":value,
                               "library":obj.data.dataId,
                               "type":obj.data.type
                              },
                              function(data,status){

                                layer.close(index);

                              })
                    });

              } else if(type === 'update'){ //修改节点

              } else if(type === 'del'){ //删除节点

              };
              return operateStatus;
            }
        });
      });
    }
}