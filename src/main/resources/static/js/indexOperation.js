LibraryOperation = {
        onAdd : function(treeId,type){
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                //获取所有library
                var nodes = treeObj.getCheckedNodes(true);
                var parents = new Array();
                $.each(nodes,function(index,ele){
                   if(ele.isParent){
                        parents.push(ele.id);
                   }else{
                        parents.push(ele.getParentNode().id);
                   }
                });
                var shelfName = $("#shelfName").value();
                var fileDirectory = $("#fileDirectory").value();

                //保存
                 $.post("/shelf/add",
                         {"name":shelfName,
                          "libraries":parents,
                          "directory":directory,
                          "type":type
                         },
                         function(data,status){
                               if(!data.id){
                                   layer.msg('添加索引失败！', {
                                     icon: 1,
                                     time: 1500 //2秒关闭（如果不配置，默认是3秒）
                                   }
                           }
                         });
           },
    onDelete : function(treeId, treeNode) {
              	if(treeNode.isParent){
                 $.post("/library/delete",
                         {"id":treeNode.id
                         },
                         function(data,status){

                         });
              	}else{
                 $.post("/shelf/delete",
                         {"id":treeNode.id
                         },
                         function(data,status){
                               if(!data.id){
                                   layer.msg('添加索引失败！', {
                                     icon: 1,
                                     time: 1500 //2秒关闭（如果不配置，默认是3秒）
                                   }
                           }
                         });
              	}
              }
}