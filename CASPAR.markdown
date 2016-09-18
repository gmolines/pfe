---
layout: page
title: CASPAR
longtitle: CryptogrAphie, Sécurité, et vie Privée dans les Applications et Réseaux
shortcut: true
permalink: /caspar/
---
{% assign sorted_posts = (site.posts | sort: 'title') %}

{% for post in sorted_posts %}
    {% if post.categories contains 'caspar' and post.categories contains 'dispo' %}
### {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ site.baseurl }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



