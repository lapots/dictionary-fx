delete(schemaName: dbAppsSchema, tableName: 'pp_product_select_value_map') {
                where("pp_question_select_value_id = '${select_value_id}' AND pm_license_product_uid='${tou_id}'")
            }
