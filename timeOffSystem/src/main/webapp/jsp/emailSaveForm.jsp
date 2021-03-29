<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="img/favicon.png">
    <title>emailSaveForm</title>
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
    <link href="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.min.css" rel="stylesheet"/>
    <script src="../resource/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>
    <script src="../resource/dropzone.min.js"></script>
    <link rel="stylesheet" href="../resource/dropzone.min.css">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>

<%
    EmployeeService employeeService = new EmployeeService();
    List<Employee> employees = employeeService.findAll();

%>

<div class="container" style="width: 491.37px;">

        <div class="row">
            <div class="col-md-12">
                <div class="alert alert-block alert-danger fade in" id="alert-info">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="sender">Sender</label>
            </div>
            <div class="col-75">
                <select id="sender" name="sender">
                    <option></option>
                    <%
                        for (Employee employee : employees) {
                    %>
                    <option value="<%=employee.getId()%>"><%=employee.getFirstName()%>&nbsp;<%=employee.getLastName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="receiver">receiver</label>
            </div>
            <div class="col-75">
                <select data-placeholder="Begin typing a name to filter..." id="receiver" name="receiver" multiple
                        class="chosen-select">
                    <%
                        for (Employee employee : employees) {
                    %>
                    <option value=""></option>
                    <option value="<%=employee.getId()%>"><%=employee.getFirstName()%>&nbsp;<%=employee.getLastName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-25">
                <label for="subject">subject</label>
            </div>
            <div class="col-75">
                <input type="text" id="subject" name="subject" placeholder="subject..">
            </div>
        </div>

        <div class="row">
            <div class="col-25">
                <label for="description">description</label>
            </div>
            <div class="col-75">
                <textarea id="description" name="description" rows="3" cols="40"></textarea>
            </div>

        </div>

        <div class="row">
            <div class="col-25">
                <label for="uploadFileDrop">myDrop</label>
            </div>
            <div class="col-75">
                <div class="dropzone dz-clickable col-md-12 col-sm-12"
                     id="uploadFileDrop"
                     style="padding-top: 7px">
                    <div class="dz-default dz-message" data-dz-message="">
                        <span>Drop files here to upload</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" hidden>
            <div class="col-75">
                <input id="file_name" name="file_name"
                       style="padding-top: 7px">
                </input>
            </div>
        </div>

        <div class="row">
            <input type="submit" value="SEND" id="save">
        </div>
</div>
</body>
</html>

<script>
    $(document).ready(function () {
        $(".chosen-select").chosen({
            no_results_text: "Oops, nothing found!"
        })
    });
</script>
<script>
    $('#alert-info').hide();
    var uploadFileOk = false;

    var myDropzone = new Dropzone("div#uploadFileDrop", {
        url: "/file-upload-controller",
        maxFilesize: 5,
        addRemoveLinks: true,
        uploadMultiple: true,
        dictResponseError: 'Server not Configured',

        maxFiles: 1,
        init: function () {
            var self = this;
            // config
            self.options.addRemoveLinks = true;
            self.options.dictRemoveFile = "Delete";
            //New file added

            self.on("maxfilesexceeded", function (file) {
                console.log("this function is ok ");
                self.removeAllFiles();
                self.addFile(file);

            });

            self.on("addedfile", function (file) {
                console.log('new file added ', file);

            });

            // Send file starts
            self.on("sending", function (file) {
                console.log('upload started', file);
                document.getElementById("file_name").value = file.name;
                $('.meter').show();
                uploadFileOk = true;
                $('#alert-info').hide();
            });

            // File upload Progress
            self.on("totaluploadprogress", function (progress) {
                console.log("progress ", progress);
                $('.roller').width(progress + '%');
            });

            self.on("queuecomplete", function (progress) {
                $('.meter').delay(999).slideUp(999);
            });

            // On removing file
            self.on("removedfile", function (file) {
                console.log(file);
                document.getElementById("file_name").value = "";
            });

        },
        success: function (file, response) {
            console.log(response);
        },
        error: function (file, response) {
            alert(response);
        }
    });

     $('#save').click(function () {
         $.ajax({
             url: '/email-controller',
             type: 'POST',
             data: {
                 sender: $("#sender").val(),
                 receiver: $("#receiver").val(),
                 description: $("#description").val(),
                 file_name: $("#file_name").val(),
                 subject: $("#subject").val(),
             },
             success: function (data) {
                 console.log(data)
                 $('#alert-info').removeClass("alert-danger");
                 $('#alert-info').addClass("alert-success");
                 $('#alert-info p').remove();
                 $('#alert-info').append(data);
                 $('#alert-info').show();
            },
            error: function (error) {
                $('#alert-info').removeClass("alert-success");
                $('#alert-info').addClass("alert-danger");
                $('#alert-info p').remove();
                $('#alert-info').append(error.responseText);
                $('#alert-info').show();
            }
        })
    });
</script>

<style>
    .fade.in {
        opacity: 1;
        display: block;
    }

    .alert-danger {
        color: #a94442;
        background-color: #f2dede;
        border-color: #ebccd1;
    }

    .alert-success {
        color: #3c763d;
        background-color: #dff0d8;
        border-color: #d6e9c6
    }


    .alert {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-top-color: transparent;
        border-right-color: transparent;
        border-bottom-color: transparent;
        border-left-color: transparent;
        border-radius: 4px;
    }
</style>
