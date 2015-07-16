'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:FileUploadCtrl
 * @description
 * # FileUploadCtrl
 * Controller for the file upload functionality
 */
angular.module('batchAdmin')
  .controller('FileUploadCtrl', ['$scope', 'FileUploader', 'configuration', 'jobService', 'ngTableParams', function ($scope, FileUploader, configuration, jobService, ngTableParams) {

    var uploader = $scope.uploader = new FileUploader({
      url: configuration.baseUrl + '/batch/files',
      method: 'POST'
    });

    uploader.onBeforeUploadItem = function (item) {
      var definedPath = '';

      if($scope.path) {
        definedPath = $scope.path;
      }

      item.formData = [{ path: definedPath }];
    };

    uploader.onCompleteAll = function() {
      $scope.tableParams.reload();
    };

    $scope.tableParams = new ngTableParams({
      page: 0,            // show first page
      count: 10           // count per page
    }, {
      getData: function ($defer, params) {
        jobService.getFiles(params.page() - 1, params.count()).then(function (response) {
          params.total(response.data.pagedResources.page.totalElements);
          $defer.resolve(response.data.pagedResources.content);
          $scope.tableData = response.data.pagedResources.page.totalElements > 0;
        });
      }
    });

    $scope.deleteFiles = function() {
      jobService.deleteFiles($scope.path);
      $scope.tableParams.reload();
    };
  }]);
