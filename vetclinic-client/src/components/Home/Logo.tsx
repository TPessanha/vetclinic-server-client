import React from 'react';

const Logo = ({appName = "VetClinic", token = null}) => {
    if (token) {
        return null;
    }
    return (
        <div className="logo">
            <div className="container center">
                {/*<h1 className="logo-font">*/}
                {/*    {appName}*/}
                {/*</h1>*/}
                <p className="text-xs-center">Welcome to Veterinarian Manegment Clinic.</p>
            </div>
        </div>
    );
};

export default Logo;
