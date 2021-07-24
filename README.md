**Install hazelcast cluster**

**sử dụng k8s Dashboard cài role và mapping role**

apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: hazelcast-cluster-role
rules:
  - apiGroups:
      - ""
    resources:
      - endpoints
      - pods
      - nodes
      - services
    verbs:
      - get
      - list
      

-----
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: hazelcast-cluster-role-binding
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: hazelcast-cluster-role
subjects:
  - kind: ServiceAccount
    name: default
    namespace: development

**----- Tạo headless service, expose port (5701 cho service trong deployment)**
kind: Service
apiVersion: v1
metadata:
  name: svc-basepad-hazelcast
spec:
  ports:
    - name: hazelcast
      protocol: TCP
      port: 5701
      targetPort: 5701
  selector:
    app: svc-basepad
  type: ClusterIP

**--- Cấu hình configmap hazelcast**
        <kubernetes enabled="true">
            <namespace>development</namespace>
            <service-dns>svc-basepad-hazelcast</service-dns>
        </kubernetes>

