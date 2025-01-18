{{ define "anticipatedObjects" -}}
	{{ $nodes := Nodes .Types }}
	{{- range $i, $type := $nodes }}
{{ "" }}
		{{- template "anticipatedObject" .}}
	{{- end }}
{{- end }}

{{ define "anticipatedObject" -}}
	{{- /* Write description. */ -}}
	{{- if .Description }}
		{{- /* Split comment string into a slice of one line per element. */ -}}
		{{- $desc := CommentToLines .Description -}}
		{{- range $desc -}}
{{ "  " }}#{{ . }}
		{{- end }}
	{{- else }}
{{- "  " }}# {{ .Name | QueryToClient | FormatName }} class
	{{- end }}
{{ "  " }}class {{ .Name | QueryToClient | FormatName }} < Node; end
	{{- if . | IsSelfChainable }}

  # Block to chain methods on {{ .Name | QueryToClient | FormatName }}
  {{ .Name | QueryToClient }}Chain = T.type_alias { T.proc.params(arg0: {{ .Name | QueryToClient | FormatName }}).returns({{ .Name | QueryToClient | FormatName }}) }
	{{- end }}
{{ "" }}
{{- end }}

{{- /* Generate class from GraphQL struct query type. */ -}}
{{ define "object" -}}
	{{- $objectName := .Name -}}
	{{- with . -}}
		{{- if .Fields }}
			{{- /* Write description. */ -}}
			{{- if .Description }}
				{{- /* Split comment string into a slice of one line per element. */ -}}
				{{- $desc := CommentToLines .Description -}}
				{{- range $desc -}}
{{ "  " }}#{{ . }}
{{ "" }}
				{{- end }}
			{{- else }}
{{- "  " }}# {{ .Name | QueryToClient | FormatName }} class
{{ "" }}
			{{- end }}
			{{- /* Write object name. */ -}}
{{ "  " }}class {{ .Name | QueryToClient | FormatName }} < Node
    extend T::Sig
{{ "" }}
      {{- $first := true -}}
      {{- /* Add custom method to main Client */ -}}
      {{- if .Name | QueryToClient | FormatName | eq "Client" }}
      {{- $first = false -}}
{{ "" }}
    sig { returns(GraphQLClient) }
    attr_reader :client

      {{- end -}}
			{{- /* Write methods. */ -}}
			{{- range $field := .Fields }}
				{{- if not $first }}
{{ "" }}
				{{- end }}
				{{- $first = false -}}
				{{- if eq $field.Name "id" }}
    # Return the Node ID for the GraphQL entity
    # @return [String]
    sig { returns(String) }
    def id
      @client.invoke(Node.new(self, @client, 'id'))
    end

    # Return the ID in json
    # @return [JSON]
    sig { returns(JSON) }
    def to_json
      JSON.from(id)
    end
					{{- if IsModuleCode }}

    # Load from the JSON ID sent
    sig { params(id: string).returns({{ .Name | QueryToClient | FormatName }}) }
    def from_json(id)
      return load_{{ $objectName | QueryToClient | ToFuncPart }}_from_id(id)
    end
					{{- end }}
				{{- else }}
					{{- template "method" $field }}
				{{- end }}
			{{- end }}
			{{- if . | IsSelfChainable }}
{{ "" }}
    sig { params(_blk: {{ .Name | QueryToClient }}Chain).returns({{ .Name | QueryToClient | FormatName }}) }
    def with(&_blk)
      yield self
    end
			{{- end }}
  end
		{{- end -}}
	{{- end -}}
{{- end -}}
