insert(schemaName: dbAppsSchema, tableName: 'pp_product_select_value_map') {
            column(name: 'pp_question_select_value_id', value: '${select_value_id}')
            column(name: 'pm_license_product_uid', value: '${tou_id}')
            column(name: 'record_version', value: '1')
            column(name: 'created_by_user_system', value: 'system')
            column(name: 'updated_by_user', value: 'system')
        }
