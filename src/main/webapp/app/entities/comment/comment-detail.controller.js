(function() {
    'use strict';

    angular
        .module('phoenixApp')
        .controller('CommentDetailController', CommentDetailController);

    CommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Comment', 'Article', 'Author'];

    function CommentDetailController($scope, $rootScope, $stateParams, entity, Comment, Article, Author) {
        var vm = this;

        vm.comment = entity;

        var unsubscribe = $rootScope.$on('phoenixApp:commentUpdate', function(event, result) {
            vm.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
