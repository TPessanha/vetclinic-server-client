import React from 'react';
import AdministratorPreview from "./AdministratorPreview";

const AdministratorList = (props: any) => {
    if (!props.articles) {
        return (
            <div className="article-preview">Loading...</div>
        );
    }

    if (props.articles.length === 0) {
        return (
            <div className="article-preview">
                No articles are here... yet.
            </div>
        );
    }

    return (
        <div>
            {
                props.administrator.map((administrator: any) => {
                    return (
                        <AdministratorPreview article={administrator} key={administrator.username}/>
                    );
                })
            }

        </div>
    );
};

export default AdministratorList;
