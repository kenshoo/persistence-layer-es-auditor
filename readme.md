# Persistence-layer-es-auditor

Setup guide:
1. load local aleastic-seach/kibana dockers: https://www.elastic.co/guide/en/kibana/current/docker.html
1. create audit index:
```
url -X PUT "localhost:9200/audit" -H 'Content-Type: application/json' -d'
 {
   "mappings": {
 	"properties": {
    	 "parentEntityType": { "type": "keyword" },
  	"parentEntityId": { "type": "keyword" },
   	"entityType": { "type": "keyword" },
  	"entityId": { "type": "keyword" },
 "operator": { "type": "keyword" },
   	"mandatoryFieldValues": {
     	"type": "nested",
     	"properties": {
         	"name": { "type": "keyword" },
         	"value": { "type": "keyword" }
     	}
   	},
    	 
   	"fieldRecords": {
     	"type": "nested",
     	"properties": {
        	 
       	"field": { "type": "keyword" },
 "oldValue": { "type": "keyword" },
 "newValue": { "type": "keyword" }
 
      	 
     	}   	 
    	 
    	 
   	}
 	}
   }
 }
 '
```
1. elastic-search API: https://logz.io/blog/elasticsearch-api/