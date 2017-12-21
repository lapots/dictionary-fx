package some-package.${release_version}.${tou_id?lower_case}

databaseChangeLog (logicalFilePath: 'some-price/${tou_id?lower_case}/${question_id?lower_case}.groovy') {

    changeSet (id: '${changeset_id}', author: '${author_name} <${author_email}>') {
        comment ('Maps ${tou_id} type of use to ${question_id} question')

        insert(schemaName: dbAppsSchema, tableName: 'pp_product_question_map') {
            column(name: 'pp_question_id', value: '${question_id}')
            column(name: 'pm_license_product_uid', value: '${tou_id}')
            column(name: 'is_required_flag', value: '${is_mandatory?string('true', 'false')}')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user_system', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }

        <#list inserts as insert>
        ${insert}
        </#list>

        rollback {
            delete(schemaName: dbAppsSchema, tableName: 'pp_product_question_map') {
                where("pp_question_id = '${question_id}' AND pm_license_product_uid='${tou_id}'")
            }
            <#list rollbacks as rollback>
            ${rollback}
            </#list>
        }
    }

}
