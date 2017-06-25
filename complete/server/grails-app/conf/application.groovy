// Added by the Spring Security Core plugin: //<1>
grails.plugin.springsecurity.userLookup.userDomainClassName = 'ice.cream.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'ice.cream.UserRole'
grails.plugin.springsecurity.authority.className = 'ice.cream.Role'
grails.plugin.springsecurity.rest.login.endpointUrl = '/login'
grails.plugin.springsecurity.useSecurityEventListener = true


grails.plugin.springsecurity.controllerAnnotations.staticRules = [ //<2>
        [pattern: '/stomp/**', access: ['permitAll']],
        [pattern: '/signup/**', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [ //<3>
        //Stateless chain
        [
                pattern: '/**',
                filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ]
]
