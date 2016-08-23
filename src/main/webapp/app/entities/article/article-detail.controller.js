(function() {
    'use strict';

    angular
        .module('phoenixApp')
        .controller('ArticleDetailController', ArticleDetailController);

    ArticleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Article', 'Author'];

    function ArticleDetailController($scope, $rootScope, $stateParams, entity, Article, Author) {
        var vm = this;

        vm.article = entity;

        var unsubscribe = $rootScope.$on('phoenixApp:articleUpdate', function(event, result) {
            vm.article = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
