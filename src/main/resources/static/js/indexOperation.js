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
                                       layer.msg('添加索引失败！',
                                                 {
                                                   icon: 1,
                                                   time: 1500 //2秒关闭（如果不配置，默认是3秒）
                                                 });
                                   }
                             }
                     );
                 },
        beforeDelete : function(treeId, treeNode) {
                    var allowed = false;
                    if(treeNode.nodeType = "lib"){
                         $.post("/library/delete",
                                 {"id":treeNode.id},
                                 function(data,status){
                                       if(!data.success){
                                           layer.msg(data.msg, {
                                             icon: 1,
                                             time: 1500 //2秒关闭（如果不配置，默认是3秒）
                                           });
                                       }
/*                                       else{
                                            var libTree = $.fn.zTree.getZTreeObj(treeId);
                                            var shelfTree = $.fn.zTree.getZTreeObj(libTree.shelfTreeId);
                                            var filter = function(node){
                                                return node.id == treeNode.id &&
                                                       node.nodeType == "shelf";
                                            }
                                            $(libTree.getNodesByFilter(filter)).each(function(index,node)
                                                libTree.removeNode(node);
                                            );
                                            $(shelfTree.getNodesByFilter(filter)).each(function(index,node)
                                                shelfTree.removeNode(node);
                                            );

                                       }*/
                                       allowed = data.success;
                                 });
                    }else if(treeNode.nodeType = "shelf"){
                         $.post("/shelf/delete",
                                 {"id":treeNode.id},
                                 function(data,status){
                                       if(!data.success){
                                           layer.msg(data.msg, {
                                             icon: 1,
                                             time: 1500 //2秒关闭（如果不配置，默认是3秒）
                                           });
                                       }else{
                                            var shelfTree = $.fn.zTree.getZTreeObj(treeId);
                                            var libTree = $.fn.zTree.getZTreeObj(libTree.shelfTreeId);
                                            var filter = function(node){
                                                return node.id == treeNode.id &&
                                                       node.nodeType == "shelf";
                                            }
                                            $(libTree.getNodesByFilter(filter)).each(function(index,node){
                                                libTree.removeNode(node);
                                            });
                                            $(shelfTree.getNodesByFilter(filter)).each(function(index,node){
                                                shelfTree.removeNode(node);
                                            });

                                       }
                                       allowed = data.success;
                                 }
                         );
                    }
                    return allowed;
              },
        beforeRename : function(treeId, treeNode, newName, isCancel){
            var allowed = true;
            if(!isCancel){
                if(treeNode.nodeType = "lib"){
                     $.post("/library/rename",
                             {"id":treeNode.id,
                              "newName":newName
                             },
                             function(data,status){
                                   if(!data.success){
                                       layer.msg(data.msg, {
                                         icon: 1,
                                         time: 1500 //2秒关闭（如果不配置，默认是3秒）
                                       });
                                   }
/*                                   else{
                                        var libTree = $.fn.zTree.getZTreeObj(treeId);
                                        var shelfTree = $.fn.zTree.getZTreeObj(libTree.shelfTreeId);
                                        var filter = function(node){
                                            return node.id == treeNode.id &&
                                                   node.nodeType == "shelf";
                                        }
                                        $(libTree.getNodesByFilter(filter)).each(function(index,node)
                                            node.name = newName;
                                        );
                                        $(shelfTree.getNodesByFilter(filter)).each(function(index,node)
                                            node.name = newName;
                                        );

                                   }*/
                                   allowed = data.success;
                             });
                }else if(treeNode.nodeType = "shelf"){
                     $.post("/shelf/rename",
                             {"id":treeNode.id,
                              "newName":newName
                             },
                             function(data,status){
                                   if(!data.success){
                                       layer.msg(data.msg, {
                                         icon: 1,
                                         time: 1500 //2秒关闭（如果不配置，默认是3秒）
                                       });
                                   }else{
                                        var shelfTree = $.fn.zTree.getZTreeObj(treeId);
                                        var libTree = $.fn.zTree.getZTreeObj(libTree.libTreeId);
                                        var filter = function(node){
                                            return node.id == treeNode.id &&
                                                   node.nodeType == "shelf";
                                        }
                                        $(libTree.getNodesByFilter(filter)).each(function(index,node){
                                            node.name = newName;
                                        });
                                        $(shelfTree.getNodesByFilter(filter)).each(function(index,node){
                                            node.name = newName;
                                        });

                                   }
                                   allowed = data.success;
                             }
                     );
                }
            }
            return allowed;
        }
}