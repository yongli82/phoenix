'use strict';

describe('Controller Tests', function() {

    describe('Comment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockComment, MockArticle, MockAuthor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockComment = jasmine.createSpy('MockComment');
            MockArticle = jasmine.createSpy('MockArticle');
            MockAuthor = jasmine.createSpy('MockAuthor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Comment': MockComment,
                'Article': MockArticle,
                'Author': MockAuthor
            };
            createController = function() {
                $injector.get('$controller')("CommentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'phoenixApp:commentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
