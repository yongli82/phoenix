(function() {
    'use strict';

    angular
        .module('phoenixApp')
        .controller('AuthorController', AuthorController);

    AuthorController.$inject = ['$scope', '$state', 'Author'];

    function AuthorController ($scope, $state, Author) {
        var vm = this;
        
        vm.authors = [];

        loadAll();

        function loadAll() {
            Author.query(function(result) {
                vm.authors = result;
            });
        }
    }
})();
