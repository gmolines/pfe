---
layout: page
title: WEB
longtitle: Sciences, Technologies, Ressources et Applications du Web
shortcut: true
permalink: /web/
---
{% assign sorted_posts = (site.posts | sort: 'title') %}

{% for post in sorted_posts %}
    {% if post.categories contains 'web' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ site.baseurl }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



