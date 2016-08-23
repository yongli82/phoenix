(function() {
    'use strict';
    angular
        .module('phoenixApp')
        .factory('Comment', Comment);

    Comment.$inject = ['$resource', 'DateUtils'];

    function Comment ($resource, DateUtils) {
        var resourceUrl =  'api/comments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.publishDate = DateUtils.convertDateTimeFromServer(data.publishDate);
                        data.updateDate = DateUtils.convertDateTimeFromServer(data.updateDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
