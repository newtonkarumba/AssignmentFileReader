(function(){
    AssignmentJsLib.showGrid.call({
        dataUrl: 'organization',
        gridStyle: 'user',
        componentId: 'organization',
        gridColumns:[{
            header: 'Name',
            dataIndex: 'name'
        },{
            header: 'Age',
            dataIndex: 'age'
        },{
              header: 'Town',
            dataIndex: 'town'
                 }],
        gridButtons: [{
            label: 'Submit',
            cssClass: 'addButton',
            handler: 'addButton',
            id: 'org-addButton',
        }],
        formField: [{
            label: 'Name',
            name: 'name',
            type: 'text',
            id: 'org-name'
        },{
            label: 'Age',
            name: 'age',
            type: 'text',
            id: 'org-age'
        },{
                     label: 'Town',
                     name: 'town',
                     type: 'text',
                     id: 'org-town'
                 }]
    });
})();