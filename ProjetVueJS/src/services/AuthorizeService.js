const AuthorizeService = {}

const authorizationAdmin = {
    admin: {
        user : {
            create: true,
            update: true,
            delete: true,
            get: true
        },
        advert : {
            create: true,
            update: true,
            delete: true,
            get: true
        }
    },
    modo: {
        user : {
            create: false,
            update: false,
            delete: false,
            get: false
        },
        advert : {
            create: false,
            update: true,
            delete: false,
            get: true
        }
    },
    client: {
        user : {
            create: false,
            update: false,
            delete: false,
            get: false
        },
        advert : {
            create: false,
            update: false,
            delete: false,
            get: false
        }
    },
    default: {
        user : {
            create: false,
            update: false,
            delete: false,
            get: false
        },
        advert : {
            create: false,
            update: false,
            delete: false,
            get: false
        }
    }
}

AuthorizeService.getAuthorization = () => {

    const role = localStorage.getItem('roles');

    if(role === undefined || role === null)
        return authorizationAdmin.default;
    else if(role === 'ROLE_ADMIN'){
        return authorizationAdmin.admin;
    }
    else if( role === 'ROLE_MODERATOR'){
        return authorizationAdmin.modo;
    }
    else if( role === 'ROLE_CLIENT'){
        return authorizationAdmin.client;
    }

    return authorizationAdmin.default;
}

export default AuthorizeService;