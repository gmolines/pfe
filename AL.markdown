---
layout: page
title: AL
longtitle: Architecture Logicielle
shortcut: true
permalink: /al/
---
{% assign sorted_posts = (site.posts | sort: 'title') %}

{% for post in sorted_posts %}
    {% if post.categories contains 'al' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ BASE_PATH }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



