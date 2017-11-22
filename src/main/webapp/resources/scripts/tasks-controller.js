tasksController = function () {

    function errorLogger(errorCode, errorMessage) {
        console.log(errorCode + ':' + errorMessage);
    }

    var taskPage;
    var initialised = false;
    var sortBy = "dueDate";

    /**
     * makes json call to server to get task list. currently just testing this
     * and writing return value out to console 111917kl
     */
    function retrieveTasksServer() {
        $.ajax("task", {
            "type": "get",
            dataType: "json"
            // "data": {
            // "first": first,
            // "last": last
            // }
        }).done(displayTasksServer.bind()); //need reference to the tasksController object
    }

    function retrieveTasksServerByTeamId(teamId) {
        $.ajax("task", {
            "type": "get",
            "data": {
                "teamId": teamId,
            }
        }).done(displayTasksServer.bind()); //need reference to the tasksController object
    }

    function retrieveTasksServerByUserId(userId) {
        $.ajax("task", {
            "type": "get",
            "data": {
                "userId": userId,
            }
        }).done(displayTasksServer.bind()); //need reference to the tasksController object
    }

    /**
     * 111917kl callback for retrieveTasksServer
     *
     * @param data
     */
    function displayTasksServer(data) { // this needs to be bound to the
        // tasksController -- used bind in
        // retrieveTasksServer 111917kl
        console.log(data);
        tasksController.loadServerTasks(data);
    }

    function taskCountChanged() {
        var count = $(taskPage).find('#tblTasks tbody tr').length;
        $('footer').find('#taskCount').text(count);
    }

    function clearTask() {
        $(taskPage).find('form').fromObject({});
    }

    function clearTeam() {
        $(taskPage).find('#frm_team').fromObject({});
    }

///////////////////////////////////////////////////
    function renderTable() {
        $.each($(taskPage).find('#tblTasks tbody tr'), function (idx, row) {
            var due = Date.parse($(row).find('[datetime]').text());
            if (due.compareTo(Date.today()) < 0) {
                $(row).addClass("overdue");
            } else if (due.compareTo((2).days().fromNow()) <= 0) {
                $(row).addClass("warning");
            }
        });
    }

    function loadTeams() {
        $.ajax('team', {
            "type": "GET",
        }).done(function (jdata) {
            $(taskPage).find('#tblTeams tbody').empty();
            $.each(jdata, function (index, team) {
                let tr = $('<tr>');
                let td1 = $('<td>').append(team.id);
                let td2 = $('<td>').append(team.name);
                let td3 = $('<td>');
                if (team.teamMemberList == null) {
                    td3.append("");
                } else {
                    $.each(team.teamMemberList, function (index, member) {
                        let span = $('<span>', {
                            'text': member.userId + ", "
                        })
                        td3.append(span);
                    })
                }
                let td4 = $('<td>');
                let nav = $('<nav>')
                    .append($('<a>', {
                        'href': '#',
                        'class': 'deleteBtnTeam',
                        'onclick': 'tasksController.deleteTeam(' + team.id + ')',
                        'text': 'Delete'
                    }))
                    .append(' ')
                    .append($('<input>').attr({
                        'type': 'text',
                        'size': '4',
                        'placeHolder': 'user id',
                        'id': 'userId_' + team.id
                    }))
                    .append(' ')
                    .append($('<a>', {
                        'href': '#',
                        'class': 'joinBtnTeam',
                        'onclick': 'tasksController.joinTeam(' + team.id + ')',
                        'text': 'Join'
                    }));
                td4.append(nav);
                tr.append(td1).append(td2).append(td3).append(td4);
                $(taskPage).find('#tblTeams tbody').append(tr);
                console.log('about to render table with server teams');
            });
        });
    }

    return {
        init: function (page, callback) {
            if (initialised) {
                callback()
            } else {
                taskPage = page;
                storageEngine.init(function () {
                    storageEngine.initObjectStore('task team', function () { callback(); }, errorLogger);
                    console.log("Team table is created");
                }, errorLogger);

                $(taskPage).find('[required="required"]').prev('label')
                    .append('<span>*</span>').children('span').addClass('required');

                $(taskPage).find('tbody tr:even').addClass('even');

                $(taskPage).find('#btnAddTask').click(function (evt) {
                    evt.preventDefault();
                    $(taskPage).find('#taskCreation').removeClass('not');
                    if (!$(taskPage).find('#teamCreation').hasClass('not'))
                        $(taskPage).find('#teamCreation').addClass('not');
                });

                $(taskPage).find('#btntoServer').click(function () {


                    storageEngine.findAll('task', function (tasks) {

                        $.each(tasks, function (index, task) {
                            //console.log(JSON.stringify(task));

                            $.ajax("task", {
                                type: "post",
                                data: JSON.stringify(task),
                                contentType: "application/json; charset=utf-8"
                            }).done(function (data) {
                                $("#msg").removeClass("not");
                                $("#msg").html(data);
                            })
                                .fail(function (data) {
                                    $("#msg").removeClass("not");
                                    $("#msg").html(data);
                                });

                        });
                    }, errorLogger);

                });


                /** * 11/19/17kl */
                $(taskPage).find('#btnRetrieveTasks').click(function (evt) {
                    evt.preventDefault();
                    console.log('making ajax call');
                    retrieveTasksServer();
                    $('#filterSection').removeClass('taskNot');
                });

                $(taskPage).find('#tblTasks tbody').on('click', 'tr', function (evt) {
                    $(evt.target).closest('td').siblings().andSelf().toggleClass('rowHighlight');
                });

                $(taskPage).find('#tblTasks tbody').on('click', '.deleteRow', function (evt) {
                    storageEngine.delete('task', $(evt.target).data().taskId, function () {
                        $(evt.target).parents('tr').remove();
                        taskCountChanged();
                    }, errorLogger);
                });

                //SDIMPL START
                $(taskPage).find('#tblTeams tbody').on('click', '.deleteRow', function (evt) {
                    storageEngine.delete('team', $(evt.target).data().taskId, function () {
                        $(evt.target).parents('tr').remove();
                        taskCountChanged();
                    }, errorLogger);
                });
                $(taskPage).find('#tblTeams tbody').on('click', '.editRow', function (evt) {
                    $(taskPage).find('#teamCreation').removeClass('not');
                    storageEngine.findById('team', $(evt.target).data().taskId, function (team) {
                        $(taskPage).find('form').fromObject(team);
                    }, errorLogger);
                });
                //SDIMPL END

                $(taskPage).find('#tblTasks tbody').on('click', '.editRow', function (evt) {
                    $(taskPage).find('#taskCreation').removeClass('not');
                    storageEngine.findById('task', $(evt.target).data().taskId, function (task) {
                        $(taskPage).find('form').fromObject(task);
                    }, errorLogger);
                });

                $(taskPage).find('#clearTask').click(function (evt) {
                    evt.preventDefault();
                    clearTask();
                });

                $(taskPage).find('#tblTasks tbody').on('click', '.completeRow', function (evt) {
                    storageEngine.findById('task', $(evt.target).data().taskId, function (task) {
                        task.complete = true;
                        storageEngine.save('task', task, function () {
                            tasksController.loadTasks();
                        }, errorLogger);
                    }, errorLogger);
                });


                /////// Edits by Ramy Badawy
                $(taskPage).find('#prioritySort').click(function (evt) {
                    //console.log(this);
                    sortBy = "priority";
                    tasksController.loadTasks();
                });

                $(taskPage).find('#dueSort').click(function (evt) {
                    //console.log(this);
                    sortBy = "dueDate";
                    tasksController.loadTasks();
                });
                // ending edits

                $(taskPage).find('#saveTask').click(function (evt) {
                    evt.preventDefault();
                    if ($(taskPage).find('form').valid()) {
                        var task = $(taskPage).find('form').toObject();
                        console.log(task);
                        //Edits By Ramy Badawy
                        // Adding order property to each task, to sort with it later on
                        let sel = $(taskPage).find('#priorityID option:selected').data("order");
                        task.order = sel;
                        // ending edits

                        storageEngine.save('task', task, function () {
                            $(taskPage).find('#tblTasks tbody').empty();
                            tasksController.loadTasks();
                            clearTask();
                            $(taskPage).find('#taskCreation').addClass('not');
                        }, errorLogger);
                    }
                });

                // SDIMPL START
                $(taskPage).find('#btnManageTeam').click(function (evt) {
                    evt.preventDefault();
                    $(taskPage).find('#teamCreation').removeClass('not');
                    if (!$(taskPage).find('#taskCreation').hasClass('not'))
                        $(taskPage).find('#taskCreation').addClass('not');

                });

                $(taskPage).find('#btnAddTeam').click(function (evt) {
                    //Edits By Ramy Badawy
                    // save to local db
                    evt.preventDefault();
                    if ($(taskPage).find('#frm_team').valid()) {
                       var team = $(taskPage).find('#frm_team').toObject();
                       console.log(team);

                       storageEngine.save('team', team, function () {
                            $(taskPage).find('#tblTeams tbody').empty();
                            tasksController.loadTeams();
                            clearTeam();
                            $(taskPage).find('#teamCreation').addClass('not');
                        }, errorLogger);
                    }

                    // save to server
                    // let teamName = $('#teamName').val();
                    // let sendInfo = {
                    //     'name': teamName
                    // };
                    // $.ajax('team', {
                    //     'type': 'POST',
                    //     dataType: "json",
                    //     data: JSON.stringify(sendInfo),
                    //     contentType: "application/json; charset=utf-8"
                    // }).done(function (data) {
                    //     $('#teamName').val('');
                    //     loadTeams();
                    // });

                });

                initialised = true;

                $(taskPage).find('#btnRetrieveTeams').click(function () {
                    loadTeams();
                });

                $(taskPage).find('#btnFilterUserId').click(function () {
                    let userId = $('#userIdFilter').val();
                    $.get('task', {
                        'userId': userId
                    }).done(function (data) {
                        tasksController.loadServerTasks(data);
                    });
                });

                $(taskPage).find('#btnFilterTeamId').click(function () {
                    let teamId = $('#teamIdFilter').val();
                    $.get('task', {
                        'teamId': teamId
                    }).done(function (data) {
                        retrieveTasksServerByTeamId(data);
                    });
                });
                // SDIMPL END
            }
        },
        /**
         * 111917kl
         * modification of the loadTasks method to load tasks retrieved from the server
         */
        loadServerTasks: function (tasks) {
            $(taskPage).find('#tblTasks tbody').empty();

            $.each(tasks, function (index, task) {
                if (!task.complete) {
                    task.complete = false;
                }
                $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                taskCountChanged();
                console.log('about to render table with server tasks');
                // renderTable(); --skip for now, this just sets style class for
                // overdue tasks 111917kl
            });
        },

        loadTasks: function () {
            $(taskPage).find('#tblTasks tbody').empty();

            storageEngine.findAll('task', function (tasks) {
                //console.log(tasks);
                tasks.sort(function (o1, o2) {
                    if (sortBy == "priority")
                        return o1.priority > o2.priority;
                    else
                        return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
                });
                $.each(tasks, function (index, task) {
                    if (!task.complete) {
                        task.complete = false;
                    }
                    $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                    taskCountChanged();
                    renderTable();
                });
            }, errorLogger);
        },

        loadTeams: function () {
            $(taskPage).find('#tblTeams tbody').empty();

            storageEngine.findAll('team', function (teams) {
                //console.log(teams);
                teams.sort(function (o1, o2) {
                    return o1.teamname > o2.teamname;
                });
                $.each(teams, function (index, team) {
                    console.log("index is " + index + " Key is " + team.id + " Team name is " + team.teamname);
                    $('#teamRow').tmpl(team).appendTo($(taskPage).find('#tblTeams tbody'));
                    // taskCountChanged();
                    // renderTable();
                });
            }, errorLogger);
        },
        //SD IMPL START
        deleteTeam: function (id) {
            $.ajax('team?id=' + id, {
                'type': 'DELETE'
            }).done(function (data) {
                loadTeams();
            });
        },
        joinTeam: function (id) {
            let userId = $('#userId_' + id).val();
            let sendInfo = {
                'id': id,
                'userId': userId
            };
            $.ajax('member', {
                'type': 'POST',
                dataType: "text",
                data: JSON.stringify(sendInfo),
                contentType: "application/json; charset=utf-8"
            }).done(function (data) {
                $('#userId_' + id).val('');
                loadTeams();
            }).fail(function (xhr, status, exception) {
                alert(xhr.responseText);
                console.log(xhr, status, exception);
            });
        }
        //SD IMPL END
    }
}();
