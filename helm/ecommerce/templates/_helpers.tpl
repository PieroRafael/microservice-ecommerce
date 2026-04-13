{{/*
_helpers.tpl - Funciones Reutilizables de Go Template
==========================================================
Convenciones de nombres centralizadas para evitar duplicación
y garantizar consistencia en todos los templates del chart.
*/}}

{{/*
Nombre completo del chart (usado en metadata.name)
*/}}
{{- define "ecommerce.fullname" -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Labels estándar para todos los recursos del chart.
Permite filtrar recursos con: kubectl get all -l part-of=ecommerce-platform
*/}}
{{- define "ecommerce.labels" -}}
part-of: ecommerce-platform
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version }}
{{- end }}
