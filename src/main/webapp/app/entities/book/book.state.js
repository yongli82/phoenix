(function() {
    'use strict';

    angular
        .module('phoenixApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book', {
            parent: 'entity',
            url: '/book?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'phoenixApp.book.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book/books.html',
                    controller: 'BookController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('book');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-detail', {
            parent: 'entity',
            url: '/book/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'phoenixApp.book.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book/book-detail.html',
                    controller: 'BookDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('book');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Book', function($stateParams, Book) {
                    return Book.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('book.new', {
            parent: 'book',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book/book-dialog.html',
                    controller: 'BookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                memo: null,
                                image: null,
                                created_by: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('book', null, { reload: true });
                }, function() {
                    $state.go('book');
                });
            }]
        })
        .state('book.edit', {
            parent: 'book',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book/book-dialog.html',
                    controller: 'BookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Book', function(Book) {
                            return Book.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book.delete', {
            parent: 'book',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book/book-delete-dialog.html',
                    controller: 'BookDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Book', function(Book) {
                            return Book.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
