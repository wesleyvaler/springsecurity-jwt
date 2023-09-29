package com.github.wesleyvaler.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyvaler.domain.entity.ItemPedido;
import com.github.wesleyvaler.domain.entity.Pedido;
import com.github.wesleyvaler.domain.enums.StatusPedido;
import com.github.wesleyvaler.rest.dto.AtualizacaoStatusPedidoDTO;
import com.github.wesleyvaler.rest.dto.InformacaoItemPedidoDTO;
import com.github.wesleyvaler.rest.dto.InformacoesPedidoDTO;
import com.github.wesleyvaler.rest.dto.PedidoDTO;
import com.github.wesleyvaler.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private PedidoService service;
	
	public PedidoController(PedidoService service) { this.service = service; 	}

	@PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid PedidoDTO dto ){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
	
	@GetMapping("{id}")
	public InformacoesPedidoDTO getById ( @PathVariable Integer id) {
		return service
				.obterPedidoCompleto(id)
				.map(p -> converter(p))
				orElseTherow(() ->
				new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
	}
	
	@PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id ,
                             @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

	private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }
	
	  private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		  private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		        if(CollectionUtils.isEmpty(itens)){
		            return Collections.emptyList();
		        }
		        return itens.stream().map(
		                item -> InformacaoItemPedidoDTO
		                            .builder().descricaoProduto(item.getProduto().getDescricao())
		                            .precoUnitario(item.getProduto().getPreco())
		                            .quantidade(item.getQuantidade())
		                            .build()
		        ).collect(Collectors.toList());
		    }
		  }
}	
