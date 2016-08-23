(function() {
    'use strict';
    angular
        .module('phoenixApp')
        .factory('Article', Article);

    Article.$inject = ['$resource', 'DateUtils'];

    function Article ($resource, DateUtils) {
        var resourceUrl =  'api/articles/:id';

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
